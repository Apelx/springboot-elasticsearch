package cn.apelx.elasticsearch;

import cn.apelx.elasticsearch.domain.Permission;
import cn.apelx.elasticsearch.domain.User;
import cn.apelx.elasticsearch.model.*;
import cn.apelx.elasticsearch.model.entity.RelationModel;
import cn.apelx.elasticsearch.repository.PermissionRepository;
import cn.apelx.elasticsearch.repository.UserRepository;
import cn.apelx.elasticsearch.util.ElasticsearchUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ElasticsearchUtils elasticsearchUtils;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private PermissionRepository permissionRepository;

    private final String INDEX_NAME = "users";

    @Test
    public void saveUser() {
        // 父文档
        List<User> users = new ArrayList<>();
        users.add(new User("USER-1", "abc1001", "张1", "张三1", 122, 342.32, LocalDateTime.now(), new RelationModel("users")));
        users.add(new User("USER-2", "abc1002", "钱2", "钱二1", 133, 43231.32, LocalDateTime.now(), new RelationModel("users")));
        users.add(new User("USER-3", "abc1003", "孙1", "孙子1", 144, 376432.1, LocalDateTime.now(), new RelationModel("users")));
        users.add(new User("USER-4", "abc1004", "李1", "李四1", 155, 2321.1, LocalDateTime.now(), new RelationModel("users")));
//        userRepository.saveAll(users); todo 父子文档关系不使用repository, 因为区分了两个JavaBean, 数据会紊乱
        List<String> list = elasticsearchUtils.bulkIndexDoc(INDEX_NAME, users);
        System.out.println(list);
    }

    @Test
    public void savePermission() {
        /**
         * TODO 注意：子文档与父文档的ID值相同，会覆盖掉父文档
         */
        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission(1L, "content1", new RelationModel("permission", "USER-1")));
        permissions.add(new Permission(2L, "content2", new RelationModel("permission", "USER-2")));
        permissions.add(new Permission(3L, "content3", new RelationModel("permission", "USER-2")));

        List<String> list = elasticsearchUtils.bulkIndexSubDoc(INDEX_NAME, permissions, "permission");
        System.out.println(list);
    }

    @Test
    public void search() {
      /*  Optional<User> optionalUser = userRepository.findById("1");
        if(optionalUser != null && optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println(user);
        }else {
            System.out.println("没有这个用户啊");
        }*/

        User user = elasticsearchUtils.findById(INDEX_NAME, "USER-1", User.class);
        System.out.println(user);

    }

    /**
     * TODO 注意：若同一个index分了多个实体类，因为都要注入@Id, 所以查询时会封装错误
     */
   /* @Test
    public void searchUser() {
        Iterable<User> all = userRepository.findAll();
        all.forEach(System.out::println);
    }*/

    /*@Test
    public void searchPms() {
        Iterable<Permission> all = permissionRepository.findAll();
        all.forEach(System.out::println);
    }*/
    @Test
    public void findAll() {
//        long count = userRepository.count();
//        System.out.println(count);

//        userRepository.searchSimilar()
//        Page<User> page = userRepository.findAll(PageRequest.of(0, 2, Sort.Direction.DESC, "money"));
//        List<User> content = page.getContent();
//        System.out.println(content);

//        Optional<User> user = userRepository.findById(1L);
//        User user1 = user.get();
//        System.out.println(user1);

//        userRepository.delete(new User(4L));
//        userRepository.deleteById(4L);

//        boolean b = userRepository.existsById(1L);
//        System.out.println(b);

//        userRepository.deleteAll();
    }

    public List<PropertiesMapping> testGetMappingJavaBean() {
        List<PropertiesMapping> propertiesMappingList = new ArrayList<>();
        // -------------------父文档 User Mapping -------------------------------

        FieldMapping classFieldMapping = new FieldMapping("text", Boolean.TRUE, "ik_max_word", "ik_max_word",
                new SubfieldMapping(new KeywordMapping("keyword", 256)));
        PropertiesMapping classPropertiesMapping = new PropertiesMapping("_class", classFieldMapping);
        propertiesMappingList.add(classPropertiesMapping);

        FieldMapping userIdFieldMapping = new FieldMapping("keyword", Boolean.TRUE);
        PropertiesMapping userIdPropertiesMapping = new PropertiesMapping("userId", userIdFieldMapping);
        propertiesMappingList.add(userIdPropertiesMapping);

        FieldMapping ageFieldMapping = new FieldMapping("integer", Boolean.TRUE);
        PropertiesMapping agePropertiesMapping = new PropertiesMapping("age", ageFieldMapping);
        propertiesMappingList.add(agePropertiesMapping);

        FieldMapping birthFieldMapping = new FieldMapping("date", Boolean.TRUE, "uuuu-MM-dd'T'HH:mm:ss");
        PropertiesMapping birthPropertiesMapping = new PropertiesMapping("birth", birthFieldMapping);
        propertiesMappingList.add(birthPropertiesMapping);

        FieldMapping firstNameFieldMapping = new FieldMapping("text", Boolean.TRUE, "ik_max_word", "ik_max_word", new SubfieldMapping(new KeywordMapping("keyword", 256)));
        PropertiesMapping firstNamePropertiesMapping = new PropertiesMapping("firstName", firstNameFieldMapping);
        propertiesMappingList.add(firstNamePropertiesMapping);

        FieldMapping lastNameFieldMapping = new FieldMapping("text", Boolean.TRUE, "ik_max_word", "ik_max_word", new SubfieldMapping(new KeywordMapping("keyword", 256)));
        PropertiesMapping lastNamePropertiesMapping = new PropertiesMapping("lastName", lastNameFieldMapping);
        propertiesMappingList.add(lastNamePropertiesMapping);

        FieldMapping moneyFieldMapping = new FieldMapping("double", Boolean.TRUE);
        PropertiesMapping moneyPropertiesMapping = new PropertiesMapping("money", moneyFieldMapping);
        propertiesMappingList.add(moneyPropertiesMapping);

        FieldMapping userGuidFieldMapping = new FieldMapping("keyword", Boolean.TRUE);
        PropertiesMapping userGuidPropertiesMapping = new PropertiesMapping("userGuid", userGuidFieldMapping);
        propertiesMappingList.add(userGuidPropertiesMapping);

        // ---------------关联关系; permission---------------
        JSONObject relations = new JSONObject();
        relations.put("users", Collections.singletonList("permission"));
        RelationshipMapping relationshipMapping = new RelationshipMapping("join", Boolean.TRUE, relations);
        PropertiesMapping uprPropertiesMapping = new PropertiesMapping("userPermissionRelation", relationshipMapping);
        propertiesMappingList.add(uprPropertiesMapping);


        // -----------------子文档 Permission Mapping --------------------------
        FieldMapping pmsIdFieldMapping = new FieldMapping("long", Boolean.TRUE);
        PropertiesMapping pmsIdPropertiesMapping = new PropertiesMapping("pmsId", pmsIdFieldMapping);
        propertiesMappingList.add(pmsIdPropertiesMapping);

        FieldMapping pmsContentFieldMapping = new FieldMapping("keyword", Boolean.TRUE);
        PropertiesMapping pmsContentPropertiesMapping = new PropertiesMapping("pmsContent", pmsContentFieldMapping);
        propertiesMappingList.add(pmsContentPropertiesMapping);

        return propertiesMappingList;
    }

    @Test
    public void testElasticsearchRestTemplate() {
        boolean b = elasticsearchUtils.putMapping(INDEX_NAME, testGetMappingJavaBean());
        System.out.println(b);
    }

}

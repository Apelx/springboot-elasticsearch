package cn.apelx.elasticsearch.config;

import cn.apelx.elasticsearch.converter.*;
import cn.apelx.elasticsearch.domain.User;
import cn.apelx.elasticsearch.serializer.LocalDateTimeDeserializer;
import cn.apelx.elasticsearch.serializer.LocalDateTimeSerializer;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.convert.impl.TemporalAccessorConverter;
import cn.hutool.core.util.TypeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Elasticsearch 配置类
 *
 * @author lx
 * @since 2020/7/17 10:14
 */
//@Configuration
public class ElasticsearchConfig /*extends AbstractElasticsearchConfiguration*/ {

    private String springElasticsearchRestUris;

    /*@PostConstruct
    public void init() {
        // Hutool 转换器添加自定义转换器
        ConverterRegistry.getInstance().putCustom(LocalDateTime.class, new LocalDateTimeConverter());
    }*/

  /*  @Bean
    @Primary
    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
*/

    /**
     * Return the {@link RestHighLevelClient} instance used to connect to the cluster. <br />
     * Annotate with {@link Bean} in case you want to expose a {@link RestHighLevelClient} instance to the
     * {@link ApplicationContext}.
     *
     * @return never {@literal null}.
     */
    /*@Override
    public RestHighLevelClient elasticsearchClient() {
        String HTTP_PREFIX = "http://";
        String HTTPS_PREFIX = "https://";
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(springElasticsearchRestUris.replaceAll(HTTP_PREFIX, "").replaceAll(HTTPS_PREFIX, ""))
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
*/
//    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        // 设置NULL值不参与序列化
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).registerModule(timeModule);
    }


    @Value("${spring.elasticsearch.rest.uris}")
    public void setSpringElasticsearchRestUris(String springElasticsearchRestUris) {
        this.springElasticsearchRestUris = springElasticsearchRestUris;
    }

   /* @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(new UserToMap(), new MapToUser(), new PermissionToMap(), new MapToPermission()));
    }*/

  /*  @ReadingConverter
    static class MapToUser implements Converter<Map<String, Object>, User> {

        @Override
        public User convert(Map<String, Object> map) {
            User user = BeanUtil.toBean(map, User.class);
            return user;
        }
    }

    @WritingConverter
    static class UserToMap implements Converter<User, Map<String, Object>> {

        @Override
        public Map<String, Object> convert(User source) {
            Map<String, Object> map = BeanUtil.beanToMap(source);
            if (source.getBirth() != null) {
                String format = source.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                map.put("birth", format);
            }
            return map;
        }
    }*/

    /*@Override
    protected Collection<String> getMappingBasePackages() {
        //Provide list of your packages where you have entities.
        Package mappingBasePackage = getClass().getPackage();
        return Collections.singleton("cn.apelx.elasticsearch.domain");
    }*/
}

package cn.apelx.elasticsearch;

import cn.apelx.elasticsearch.domain.User;
import cn.apelx.elasticsearch.model.entity.RelationModel;
import cn.apelx.elasticsearch.util.ElasticsearchUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.terms.*;
import org.elasticsearch.search.aggregations.metrics.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ElasticsearchApplicationTests2 {


    @Autowired
    private ElasticsearchUtils elasticsearchUtils;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    private final String INDEX_NAME = "users";


    @Test
    public void update() throws JsonProcessingException {
//        User user = new User("USER-2", null, "钱2", "钱网吧2", 133, 43231.32, LocalDateTime.now(), new RelationModel("users"));
        User user = new User("USER-2", null, "钱2", "钱网吧", 13, 123.3, LocalDateTime.now(), new RelationModel("users"));
        UpdateResponse.Result result = elasticsearchUtils.updateDoc(INDEX_NAME, user);
        System.out.println(result);

        /*cn.apelx.elasticsearch.domain.Test test = new cn.apelx.elasticsearch.domain.Test("TEST-2", "修改测试内容22222");
        String s = objectMapper.writeValueAsString(test);
//        UpdateQuery updateQuery = UpdateQuery.builder("TEST-2").withDocument(Document.parse(s)).build();
        Map<String, Object> map = BeanUtils.beanToMap(test);
        UpdateQuery updateQuery = UpdateQuery.builder("TEST-2").withDocument(Document.from(map)).build();
        UpdateResponse test1 = elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("test"));
        System.out.println(test1.getResult());*/
    }


    @Test
    public void search() {
//        String fields[] = {"userId", "userGuid", "firstName", "lastName", "age", "money", "birth", "userPermissionRelation"};

       /* NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery()
//                .withPageable(PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "money")))
//                .withFields(fields)
                .wi
                .build();*/
//        HasChildQueryBuilder hasChildQueryBuilder = new HasChildQueryBuilder("permission", QueryBuilders.matchAllQuery(), ScoreMode.Max);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.existsQuery("userId"))
                .build();
      /*  NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();*/
     /*   NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(new HasParentQueryBuilder("users", QueryBuilders.matchAllQuery(), true))
                .build();*/

        SearchHits<User> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, User.class);
    /*    System.out.println("searchHits totalHits " + searchHits.getTotalHits());
        System.out.println("searchHits totalHitsRelation " + searchHits.getTotalHitsRelation());
        System.out.println("searchHits maxScore " + searchHits.getMaxScore());
        System.out.println("searchHits hasSearchHits " + searchHits.hasSearchHits());*/
        searchHits.forEach(searchHit -> {
            System.out.println("--------------------");
            User content = searchHit.getContent();
            System.out.println(content);
        });


    }

    @Test
    public void testSearch() {
       /* MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "money"));
        List<User> search = elasticsearchUtils.search(INDEX_NAME, User.class, matchAllQueryBuilder, null, pageRequest, null);
        search.forEach(System.out::println);

        System.out.println("-------------------");
        MatchAllQueryBuilder matchAllQueryBuilder1 = QueryBuilders.matchAllQuery();
        PageRequest pageRequest1 = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "money"));
        List<User> search1 = elasticsearchUtils.search(INDEX_NAME, User.class, matchAllQueryBuilder1, null, pageRequest1, null);
        search1.forEach(System.out::println);

        System.out.println("-------------------");
        MatchAllQueryBuilder matchAllQueryBuilder2 = QueryBuilders.matchAllQuery();
        PageRequest pageRequest2 = PageRequest.of(3, 2, Sort.by(Sort.Direction.DESC, "money"));
        List<User> search2 = elasticsearchUtils.search(INDEX_NAME, User.class, matchAllQueryBuilder2, null, pageRequest2, null);
        System.out.println(search2.size());*/

       /* String fields[] = {"userId", "userGuid"};
        HasChildQueryBuilder hasChildQueryBuilder = new HasChildQueryBuilder("permission", QueryBuilders.matchAllQuery(), ScoreMode.Max);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(hasChildQueryBuilder);
        boolQueryBuilder.must(QueryBuilders.matchQuery("lastName", "张三1"));
        List<User> search = elasticsearchUtils.search(INDEX_NAME, User.class, boolQueryBuilder, null, null, fields);
        search.forEach(System.out::println);*/


      /*  BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("userGuid", "abc1001"));
        ConstantScoreQueryBuilder constantScoreQueryBuilder1 = QueryBuilders.constantScoreQuery(boolQueryBuilder1);
        SearchHits<User> search1 = elasticsearchUtils.search(INDEX_NAME, User.class, null, constantScoreQueryBuilder1);
        search1.forEach(searchHit -> System.out.println(searchHit.toString()));*/

        System.out.println("------------");

      /*  BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("userGuid", "abc1001"));
        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(boolQueryBuilder);
        SearchHits<User> search = elasticsearchUtils.search(INDEX_NAME, User.class, constantScoreQueryBuilder, (QueryBuilder) null);
        search.forEach(searchHit -> System.out.println(searchHit.toString()));*/

    }

    @Test
    public void multiGet() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIds(Arrays.asList("USER-1", "USER-2"))
                .build();
        List<User> users = elasticsearchRestTemplate.multiGet(nativeSearchQuery, User.class, IndexCoordinates.of(INDEX_NAME));
        System.out.println(users);
    }

    @Test
    public void agg() {
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("upr").field("userGuid");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.existsQuery("userId"))
                .addAggregation(termsAggregationBuilder)
                //结果集过滤  这里设置不需要结果集(不添加包含与不包含,会自动生成length为0数组)
                .withSourceFilter(new FetchSourceFilterBuilder().build())
                .build();
        SearchHits<User> search = elasticsearchRestTemplate.search(nativeSearchQuery, User.class);
        Aggregations aggregations = search.getAggregations();

       /* List<Aggregation> aggregationList = aggregations.asList();
        aggregationList.forEach(aggregation -> {
            System.out.println("name " + aggregation.getName());
            System.out.println("type " + aggregation.getType());
            System.out.println("metaData " + aggregation.getMetaData());
            System.out.println("isFragment " + aggregation.isFragment());
            System.out.println(aggregation.toString());
        });*/
        assert aggregations != null;
        ParsedTerms upr = aggregations.get("upr");
        List<? extends Terms.Bucket> buckets = upr.getBuckets();
        //获取桶
        buckets.forEach(bucket -> {
            System.out.println(bucket.toString());
            //获取桶中的key   与    记录数
            System.out.println(bucket.getKey() + " " + bucket.getDocCount());
        });
/*
        System.out.println("----------------");
        search.forEach(searchHit -> {
            Permission content = searchHit.getContent();
            System.out.println(content);
        });*/
    }


    @Test
    public void testSearchAgg() {
        //  QueryBuilders.matchQuery("userId", "USER-1")
       /* SearchHits<Permission> search = elasticsearchUtils.search(INDEX_NAME, Permission.class, null, null,
                AggregationBuilders.terms("upr").field("pmsId"), null, null);
        List<Permission> users = elasticsearchUtils.searchHitsConvertList(search);
        System.out.println(users);
        System.out.println("-----------");


        Aggregations aggregations = search.getAggregations();
        ((ParsedTerms) aggregations.get("upr")).getBuckets().forEach(
                bucket -> {
                    System.out.println(bucket.getKey() + " " + bucket.getDocCount());
                }
        );*/
//        AvgAggregationBuilder age = AggregationBuilders.avg("age").field("age");
//        SumAggregationBuilder age = AggregationBuilders.sum("age").field("age");
//        ValueCountAggregationBuilder age = AggregationBuilders.count("age").field("age");
        StatsAggregationBuilder age = AggregationBuilders.stats("age").field("age");
        SearchHits<User> searchHits = elasticsearchUtils.aggLimitSearch(INDEX_NAME, User.class, age);
        Aggregations aggregations = searchHits.getAggregations();
//        ParsedSingleValueNumericMetricsAggregation parsedSingleValueNumericMetricsAggregation = aggregations.get("age");
//        System.out.println(parsedSingleValueNumericMetricsAggregation.getType() + " -> " + parsedSingleValueNumericMetricsAggregation.value());

     /*   ParsedStats parsedStats = aggregations.get("age");
        System.out.println("type -> " + parsedStats.getType());
        System.out.println("avg -> " + parsedStats.getAvgAsString());
        System.out.println("max -> " + parsedStats.getMaxAsString());
        System.out.println("min -> " + parsedStats.getMinAsString());
        System.out.println("sum -> " + parsedStats.getSumAsString());
        System.out.println("count -> " + parsedStats.getCount());*/

        ParsedValueCount parsedValueCount = aggregations.get("age");
        System.out.println(parsedValueCount.getType() + " -> " + parsedValueCount.getValueAsString());
    }

    @Test
    public void testAggNested() {
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("upr").field("userPermissionRelation").subAggregation(
                AggregationBuilders.stats("stats").field("age")
        );

        SearchHits<User> searchHits = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.existsQuery("userId"))
                .withSourceFilter(new FetchSourceFilterBuilder().build())
                .addAggregation(termsAggregationBuilder).build(), User.class);

//        SearchHits<User> searchHits = elasticsearchUtils.aggLimitSearch(INDEX_NAME, User.class, termsAggregationBuilder);
        Aggregations aggregations = searchHits.getAggregations();


        ParsedTerms parsedTerms = aggregations.get("upr");
        List<? extends Terms.Bucket> buckets = parsedTerms.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKey() + " ->" + bucket.getDocCount());
            ParsedStats parsedStats = bucket.getAggregations().get("stats");
            System.out.println("type -> " + parsedStats.getType());
            System.out.println("avg -> " + parsedStats.getAvgAsString());
            System.out.println("max -> " + parsedStats.getMaxAsString());
            System.out.println("min -> " + parsedStats.getMinAsString());
            System.out.println("sum -> " + parsedStats.getSumAsString());
            System.out.println("count -> " + parsedStats.getCount());
            System.out.println("**************************************8");
        });



    }
}

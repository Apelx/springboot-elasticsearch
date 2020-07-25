package cn.apelx.elasticsearch;

import cn.apelx.elasticsearch.domain.Permission;
import cn.apelx.elasticsearch.domain.User;
import cn.apelx.elasticsearch.util.ElasticsearchConvertUtils;
import cn.apelx.elasticsearch.util.ElasticsearchUtils;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * TODO
 *
 * @author lx
 * @since 2020/7/24 14:49
 */
@SpringBootTest
public class AggTest {

    @Autowired
    private ElasticsearchUtils elasticsearchUtils;
    private final String INDEX_NAME = "users";

    @Test
    public void testAggSearch() {
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("groupByRelation").field("userPermissionRelation")
                .subAggregation(AggregationBuilders.stats("stats").field("age"));
        SearchHits<User> searchHits = elasticsearchUtils.aggSearch(INDEX_NAME, User.class, termsAggregationBuilder);
        List<? extends Terms.Bucket> groupByRelation = ElasticsearchConvertUtils.searchHitsConvertTermsBuckets(searchHits, "groupByRelation");
        if (!CollectionUtils.isEmpty(groupByRelation)) {
            groupByRelation.forEach(bucket -> {
                System.out.println(bucket.getKey() + " -> " + bucket.getDocCount());
                Aggregations subAggregations = bucket.getAggregations();
                if (subAggregations != null) {
                    ParsedStats parsedStats = subAggregations.get("stats");
                    if (parsedStats != null) {
                        System.out.println("type -> " + parsedStats.getType());
                        System.out.println("avg -> " + parsedStats.getAvg());
                        System.out.println("max -> " + parsedStats.getMax());
                        System.out.println("min -> " + parsedStats.getMin());
                        System.out.println("sum -> " + parsedStats.getSum());
                        System.out.println("count -> " + parsedStats.getCount());
                    }
                }
                System.out.println("*************");
            });
        }
    }

    @Test
    public void testAggLimitSearch() {
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("pmsIdTerm").field("pmsId")
                .subAggregation(AggregationBuilders.range("pmsIdRange")
                        .addRange(0, 1)
                        .addRange(1, 2)
                        .addRange(2, 3)
                        .addRange(3, 4)
                        .field("pmsId"));
        SearchHits<Permission> searchHits = elasticsearchUtils.aggLimitSearch(INDEX_NAME, Permission.class, termsAggregationBuilder);
        List<? extends Terms.Bucket> pmsIdTerm = ElasticsearchConvertUtils.searchHitsConvertTermsBuckets(searchHits, "pmsIdTerm");
        if (!CollectionUtils.isEmpty(pmsIdTerm)) {
            pmsIdTerm.forEach(bucket -> {
                System.out.println(bucket.getKey() + " -> " + bucket.getDocCount());
                System.out.println("------------------------------------");
                List<? extends Range.Bucket> pmsRange = ElasticsearchConvertUtils.termsBucketConvertRangeBucket(bucket, "pmsIdRange");
                if (pmsRange != null) {
                    pmsRange.forEach(bucketRange -> {
                        System.out.println(bucketRange.getKey() + " --> " + bucketRange.getDocCount());
                    });
                }
                System.out.println("************************************");
            });
        }
    }
}

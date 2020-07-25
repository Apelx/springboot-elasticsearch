package cn.apelx.elasticsearch.domain;

import cn.apelx.elasticsearch.model.entity.RelationModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author lx
 * @since 2020/7/17 13:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "users", replicas = 1, shards = 1, createIndex = true)
public class User implements Serializable {

    @Id
    private String userId;

    @Field(type = FieldType.Keyword)
    private String userGuid;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String firstName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String lastName;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Double)
    private Double money;

    /**
     * 1. Jackson日期时间序列化问题：
     * Cannot deserialize value of type `java.time.LocalDateTime` from String "2020-06-04 15:07:54": Failed to deserialize java.time.LocalDateTime: (java.time.format.DateTimeParseException) Text '2020-06-04 15:07:54' could not be parsed at index 10
     * 解决：@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     * 2. 日期在ES存为long类型
     * 解决：需要加format = DateFormat.custom
     * 3. java.time.DateTimeException: Unable to obtain LocalDate from TemporalAccessor: {DayOfMonth=5, YearOfEra=2020, MonthOfYear=6},ISO of type java.time.format.Parsed
     * 解决：pattern = "uuuu-MM-dd HH:mm:ss" 即将yyyy改为uuuu，或8uuuu: pattern = "8uuuu-MM-dd HH:mm:ss"
     * 参考：https://www.elastic.co/guide/en/elasticsearch/reference/current/migrate-to-java-time.html#java-time-migration-incompatible-date-formats
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime birth;


    private RelationModel userPermissionRelation;

}

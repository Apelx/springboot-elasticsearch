package cn.apelx.elasticsearch.domain;

import cn.apelx.elasticsearch.model.entity.RelationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 权限
 *
 * @author lx
 * @since 2020/7/21 10:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "users", replicas = 1, shards = 1, createIndex = true)
public class Permission implements Serializable {

    @Id
    private Long pmsId;

    @Field(type = FieldType.Keyword)
    private String pmsContent;

    private RelationModel userPermissionRelation;

}
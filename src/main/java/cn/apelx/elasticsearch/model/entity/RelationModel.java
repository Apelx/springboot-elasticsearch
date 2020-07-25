package cn.apelx.elasticsearch.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 关系Model
 *
 * @author lx
 * @since 2020/7/20 23:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationModel {

    /**
     * 关系名称
     */
    private @NonNull
    String name;

    /**
     * 父文档ID
     */
    private @Nullable
    String parent;

    public RelationModel(String name) {
        this.name = name;
    }
}

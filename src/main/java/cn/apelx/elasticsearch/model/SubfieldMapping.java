package cn.apelx.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 子字段Mapping
 *
 * @author lx
 * @since 2020/7/17 17:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubfieldMapping {
    /**
     * keyword 映射
     */
    private KeywordMapping keyword;

}

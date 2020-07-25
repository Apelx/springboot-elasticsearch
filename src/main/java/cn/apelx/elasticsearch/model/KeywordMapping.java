package cn.apelx.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Keyword Mapping
 *
 * @author lx
 * @since 2020/7/17 17:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeywordMapping {

    /**
     * 子字段类型
     */
    private String type;

    /**
     * 字段长度限制
     */
    @JsonProperty(value = "ignore_above")
    private Integer ignoreAbove;

    public KeywordMapping(String type) {
        this.type = type;
    }
}

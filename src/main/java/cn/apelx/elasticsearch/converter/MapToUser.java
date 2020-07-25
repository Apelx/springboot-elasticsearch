package cn.apelx.elasticsearch.converter;

import cn.apelx.elasticsearch.domain.User;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Map;

/**
 * TODO
 *
 * @author lx
 * @since 2020/7/22 21:25
 */
@ReadingConverter
public class MapToUser implements Converter<Map<String, Object>, User> {

    @Override
    public User convert(Map<String, Object> map) {
        return BeanUtil.toBean(map, User.class);
    }
}

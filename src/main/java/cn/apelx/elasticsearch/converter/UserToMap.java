package cn.apelx.elasticsearch.converter;

import cn.apelx.elasticsearch.domain.User;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * TODO
 *
 * @author lx
 * @since 2020/7/22 21:26
 */
@WritingConverter
public class UserToMap implements Converter<User, Map<String, Object>> {

    @Override
    public Map<String, Object> convert(User source) {
        Map<String, Object> map = BeanUtil.beanToMap(source);
        if (source.getBirth() != null) {
            String format = source.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            map.put("birth", format);
        }
        return map;
    }
}
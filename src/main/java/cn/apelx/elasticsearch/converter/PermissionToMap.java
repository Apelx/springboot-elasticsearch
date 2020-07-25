package cn.apelx.elasticsearch.converter;

import cn.apelx.elasticsearch.domain.Permission;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * TODO
 *
 * @author lx
 * @since 2020/7/22 21:30
 */
public class PermissionToMap implements Converter<Permission, Map<String, Object>> {
    @Override
    public Map<String, Object> convert(Permission permission) {
        return BeanUtil.beanToMap(permission);
    }
}

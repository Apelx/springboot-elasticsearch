package cn.apelx.elasticsearch.converter;

import cn.apelx.elasticsearch.domain.Permission;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Map;

/**
 * TODO
 *
 * @author lx
 * @since 2020/7/22 21:28
 */
@ReadingConverter
public class MapToPermission implements Converter<Map<String, Object>, Permission> {
    @Override
    public Permission convert(Map<String, Object> map) {
        Permission permission = BeanUtil.toBean(map, Permission.class);
        return permission.getPmsId() == null ? new Permission() : permission;
    }
}

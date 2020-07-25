package cn.apelx.elasticsearch.repository;

import cn.apelx.elasticsearch.domain.Permission;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 权限仓库
 *
 * @author lx
 * @since 2020/7/21 10:26
 */
public interface PermissionRepository extends ElasticsearchRepository<Permission, Long> {
}

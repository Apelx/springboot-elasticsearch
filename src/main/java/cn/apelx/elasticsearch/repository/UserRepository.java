package cn.apelx.elasticsearch.repository;

import cn.apelx.elasticsearch.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 用户仓库
 *
 * @author lx
 * @since 2020/7/17 14:12
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {

}

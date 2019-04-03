package ipmiServer.bean.dao;

import ipmiServer.bean.IPMIHost;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户服务数据接口类
 *
 * @author zhengyi
 * @since 2018-03-12
 */

@Repository
public interface IPMIHostRepository extends JpaRepository<IPMIHost, Long> {

    List<IPMIHost> findAll(Specification<IPMIHost> specification);
}
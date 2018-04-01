package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snow.myticket.bean.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    /**
     * 根据账号获取管理员实体
     * @param account 账号
     * @return 管理员实体
     */
    Manager findByAccount(String account);
}

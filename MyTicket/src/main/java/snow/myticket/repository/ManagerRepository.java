package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import snow.myticket.bean.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    /**
     * 根据账号获取管理员实体
     * @param account 账号
     * @return 管理员实体
     */
    Manager findByAccount(String account);

    /**
     * 平台增加收入
     * @param account 账号
     * @param sum 收入金额
     */
    @Transactional
    @Modifying
    @Query("UPDATE Manager m SET m.income = m.income + ?2 WHERE m.account = ?1")
    void addIncome(String account, Double sum);
}

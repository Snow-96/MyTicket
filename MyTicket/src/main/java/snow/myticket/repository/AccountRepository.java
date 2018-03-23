package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import snow.myticket.bean.Account;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    /**
     * 根据账号获取账户实体
     * @param account 账号
     * @return 账户实体
     */
    Account findByAccount(String account);

    /**
     * 从账户余额中扣钱
     * @param account 账号
     * @param sum 扣除总金额
     */
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance - ?2 WHERE a.account = ?1")
    void deductBalance(String account, Double sum);
}

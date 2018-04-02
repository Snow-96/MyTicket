package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import snow.myticket.bean.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Integer>{
    /**
     * 通过邮箱，查询会员
     * @param email 邮箱
     * @return 会员实体
     */
    Member findByEmail(String email);

    /**
     * 通过ID，查询会员
     * @param memberId 会员ID
     * @return 会员实体
     */
    Member findById(Integer memberId);

    /**
     * 根据有效性，查找会员实体
     * @param isValid 是否有效
     * @return 会员列表
     */
    List<Member> findByIsValid(Integer isValid);

    /**
     * 取消会员资格/不可恢复
     * @param email 会员邮箱
     */
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.isValid = 0 WHERE m.email = ?1")
    void cancelMember(String email);

    /**
     * 修改会员信息
     * @param username 昵称
     * @param password 密码
     * @param email 邮箱
     */
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.username = ?1, m.password = ?2 WHERE m.email = ?3")
    void modifyMemberInfo(String username, String password, String email);

    /**
     * 会员升级
     * @param memberId 会员ID
     */
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.level = m.level + 1 WHERE m.id = ?1")
    void upgrade(Integer memberId);

    /**
     * 增加会员积分
     * @param memberId 会员ID
     * @param point 会员增加积分
     */
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.point = m.point + ?2 WHERE m.id = ?1")
    void addPoints(Integer memberId, int point);

    /**
     * 扣除会员积分
     * @param memberId 会员ID
     * @param point 会员扣除积分
     */
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.point = m.point - ?2 WHERE m.id = ?1")
    void deductPoints(Integer memberId, int point);

    /**
     * 从会员余额中扣钱
     * @param memberId 会员ID
     * @param sum 扣除总金额
     */
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.balance = m.balance - ?2 WHERE m.id = ?1")
    void deductBalance(Integer memberId, Double sum);

    /**
     * 向会员余额中退钱
     * @param memberId 会员ID
     * @param sum 退还总金额
     */
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.balance = m.balance + ?2 WHERE m.id = ?1")
    void returnBalance(Integer memberId, Double sum);
}

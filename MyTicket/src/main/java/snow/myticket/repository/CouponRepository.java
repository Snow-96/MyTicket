package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import snow.myticket.bean.Coupon;

import java.util.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer>{

    /**
     * 根据会员ID，获取该会员全部有效的优惠券
     * @param memberId 会员ID
     * @param status 优惠券状态
     * @param now 当前时间
     * @return 优惠券列表
     */
    List<Coupon> findByMemberIdAndStatusAndExpirationDateAfter(Integer memberId, Integer status, Date now);

    /**
     * 设置优惠券无效
     * @param couponId 优惠券
     */
    @Transactional
    @Modifying
    @Query("UPDATE Coupon c SET c.status = -1 WHERE c.id = ?1")
    void setCouponInvalid(Integer couponId);
}

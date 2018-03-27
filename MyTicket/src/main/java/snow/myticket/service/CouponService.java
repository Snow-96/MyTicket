package snow.myticket.service;

import snow.myticket.bean.Coupon;

import java.util.List;
import java.util.Map;

public interface CouponService {

    /**
     * 根据会员ID，获得会员全部有效优惠券(过滤过期的和已使用的)
     * @param memberId 会员ID
     * @return 优惠券列表
     */
    List<Coupon> getCoupons(Integer memberId);

    /**
     * 增加优惠券
     * @param coupon 优惠券实体
     */
    void addCoupon(Coupon coupon);

    /**
     * 使用优惠券
     * @param couponId 优惠券ID
     */
    void useCoupon(Integer couponId);
}

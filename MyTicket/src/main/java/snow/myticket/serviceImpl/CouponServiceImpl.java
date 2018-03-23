package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Coupon;
import snow.myticket.repository.CouponRepository;
import snow.myticket.service.CouponService;
import snow.myticket.service.MemberService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberService memberService;

    @Override
    public List<Coupon> getCoupons(Integer memberId) {
        return couponRepository.findByMemberIdAndStatusAndExpirationDateAfter(memberId,0,new Date());
    }

    @Override
    public Integer convertCoupons(Coupon coupon, Integer amount) {
        //记录最后会员积分
        int currentPoints = 0;
        //设置过期时间
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DAY_OF_MONTH, 10);// 今天+10天
        Date expirationDate = c.getTime();

        for(int i=0;i<amount;i++){
            currentPoints = memberService.deductPoints(coupon.getMemberId(),coupon.getNeedPoints());
            coupon.setExpirationDate(expirationDate);
            couponRepository.save(coupon);
        }

        return currentPoints;
    }

    @Override
    public void useCoupon(Integer couponId) {
        couponRepository.setCouponInvalid(couponId);
    }
}

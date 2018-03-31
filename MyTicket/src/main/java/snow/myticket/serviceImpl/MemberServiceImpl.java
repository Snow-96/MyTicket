package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Coupon;
import snow.myticket.bean.Member;
import snow.myticket.bean.Orders;
import snow.myticket.repository.MemberRepository;
import snow.myticket.service.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final OrdersService ordersService;
    private final ActivityService activityService;
    private final CouponService couponService;
    private final AccountService accountService;

    private final double MEMBER_LEVEL_DISCOUNT = 0.03;
    private final int MEMBER_POINT_RATE = 10;
    private final int MEMBER_UPGRADE_STANDARD = 500;
    private final int MEMBER_MAX_LEVEL = 10;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository,OrdersService ordersService, ActivityService activityService, CouponService couponService, AccountService accountService) {
        this.memberRepository = memberRepository;
        this.ordersService = ordersService;
        this.activityService = activityService;
        this.couponService = couponService;
        this.accountService = accountService;
    }

    @Override
    public Member getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public void createMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void cancelMember(String email) {
        memberRepository.cancelMember(email);
    }

    @Override
    public void modifyMemberInfo(Member member) {
        memberRepository.modifyMemberInfo(member.getUsername(),member.getPassword(),member.getEmail());
    }

    @Override
    public boolean checkMemberBalance(Integer memberId, Double sum) {
        Member member = memberRepository.findById(memberId);
        return member.getBalance() >= sum;
    }

    @Override
    public Double calculateTotalPrice(Orders orders) {
        Activity activity = activityService.getActivity(orders.getActivityId());
        //计算订单总价
        Double sum;
        if(orders.getSeatStatus() == 1) //选座购买
            sum = orders.getFirstAmount() * activity.getFirstClassPrice() + orders.getSecondAmount() * activity.getSecondClassPrice() + orders.getThirdAmount() * activity.getThirdClassPrice();
        else //未选座购买
            sum = orders.getRandomAmount() * (activity.getFirstClassPrice() + activity.getSecondClassPrice() + activity.getThirdClassPrice())/3;

        sum *= 1 - getMemberLevel(orders.getMemberId()) * MEMBER_LEVEL_DISCOUNT;
        if(orders.getCouponId() != -1) {//若使用优惠券
            Coupon coupon = couponService.getCouponById(orders.getCouponId());
            sum *= coupon.getDiscount();
        }
        return sum;
    }

    @Override
    public Orders reserveOrders(Orders orders) {
        //使用优惠券
        couponService.useCoupon(orders.getCouponId());
        //在活动中减去对应预定的座位数
        if(orders.getSeatStatus() == 1) {
            activityService.deductSeats(orders.getActivityId(), 1, orders.getFirstAmount());
            activityService.deductSeats(orders.getActivityId(), 2, orders.getSecondAmount());
            activityService.deductSeats(orders.getActivityId(), 3, orders.getThirdAmount());
        }
        //设置订单预定时间
        orders.setReserveDate(new Date());
        //设置订单状态
        orders.setStatus(0);
        return ordersService.createOrders(orders);
    }

    @Override
    public Map<String,String> cancelOrders(Orders orders) {
        long between=(new Date().getTime() - orders.getPayDate().getTime())/1000;//除以1000是为了转换成秒
        long day=between/(24*3600);
        double rate = day < 3 ? 0.8 : 0.5;
        double back = orders.getTotalPrice() * rate;
        double currentBalance = returnBalance(orders.getMemberId(),back);
        ordersService.cancelOrders(orders);
        //在活动中增加对应预定的座位数
        if(orders.getSeatStatus() == 1 || orders.getSeatStatus() == -1) {
            activityService.addSeats(orders.getActivityId(), 1, orders.getFirstAmount());
            activityService.addSeats(orders.getActivityId(), 2, orders.getSecondAmount());
            activityService.addSeats(orders.getActivityId(), 3, orders.getThirdAmount());
        }

        Map<String,String> result = new HashMap<>();
        result.put("返还金额", String.valueOf(back));
        result.put("会员余额", String.valueOf(currentBalance));
        return result;
    }

    @Override
    public Map<String,String> payByMemberAccount(Integer ordersId) {
        Orders orders = ordersService.getOrders(ordersId);
        //会员余额中扣钱
        double currentBalance = deductBalance(orders.getMemberId(),orders.getTotalPrice());
        //根据消费情况，增加积分
        Integer currentPoints = addPoints(orders.getMemberId(), (int) (orders.getTotalPrice()/MEMBER_POINT_RATE));
        //根据消费情况，升级等级
        Integer currentLevel = getMemberLevel(orders.getMemberId());
        if(currentLevel < MEMBER_MAX_LEVEL && orders.getTotalPrice() > MEMBER_UPGRADE_STANDARD)
            currentLevel = upgrade(orders.getMemberId());
        //将订单状态设置为已支付
        ordersService.payOrders(orders.getId(),new Date());

        Map<String,String> result = new HashMap<>();
        result.put("会员余额", String.valueOf(currentBalance));
        result.put("会员积分", String.valueOf(currentPoints));
        result.put("会员等级", String.valueOf(currentLevel));
        return result;
    }

    @Override
    public Map<String,String> payByExternalAccount(Integer ordersId, String account) {
        Orders orders = ordersService.getOrders(ordersId);
        //从支付账户的余额中扣钱
        double currentBalance = accountService.deductAccountBalance(account,orders.getTotalPrice());
        //根据消费情况，增加积分
        Integer currentPoints = addPoints(orders.getMemberId(), (int) (orders.getTotalPrice()/MEMBER_POINT_RATE));
        //根据消费情况，升级等级
        Integer currentLevel = getMemberLevel(orders.getMemberId());
        if(currentLevel < MEMBER_MAX_LEVEL && orders.getTotalPrice() > MEMBER_UPGRADE_STANDARD)
            currentLevel = upgrade(orders.getMemberId());
        //将订单状态设置为已支付
        ordersService.payOrders(orders.getId(),new Date());

        Map<String,String> result = new HashMap<>();
        result.put("账户余额", String.valueOf(currentBalance));
        result.put("会员积分", String.valueOf(currentPoints));
        result.put("会员等级", String.valueOf(currentLevel));
        return result;
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
        coupon.setExpirationDate(expirationDate);
        //设置有效
        coupon.setStatus(0);

        for(int i=0;i<amount;i++){
            currentPoints = deductPoints(coupon.getMemberId(),coupon.getNeedPoints());
            couponService.addCoupon(coupon);
        }

        return currentPoints;
    }

    private Integer upgrade(Integer memberId) {
        memberRepository.upgrade(memberId);
        return memberRepository.findById(memberId).getLevel();
    }

    private Integer addPoints(Integer memberId, int point) {
        memberRepository.addPoints(memberId,point);
        return memberRepository.findById(memberId).getPoint();
    }

    private Integer deductPoints(Integer memberId, int point) {
        memberRepository.deductPoints(memberId,point);
        return memberRepository.findById(memberId).getPoint();
    }

    private Double deductBalance(Integer memberId, Double sum) {
        memberRepository.deductBalance(memberId,sum);
        return memberRepository.findById(memberId).getBalance();
    }

    private Double returnBalance(Integer memberId, Double sum) {
        memberRepository.returnBalance(memberId,sum);
        return memberRepository.findById(memberId).getBalance();
    }

    private Integer getMemberLevel(Integer memberId) {
        return memberRepository.findById(memberId).getLevel();
    }


}

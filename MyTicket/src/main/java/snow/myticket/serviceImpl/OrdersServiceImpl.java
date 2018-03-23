package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Coupon;
import snow.myticket.bean.Orders;
import snow.myticket.repository.OrdersRepository;
import snow.myticket.service.*;

import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private ActivityService activityService;

    private final double MEMBER_LEVEL_DISCOUNT = 0.03;
    private final int MEMBER_POINT_RATE = 10;
    private final int MEMBER_UPGRADE_STANDARD = 500;
    private final int MEMBER_MAX_LEVEL = 10;

    @Override
    public List<Orders> getMemberOrders(Integer memberId) {
        return ordersRepository.findByMemberId(memberId);
    }

    @Override
    public List<Orders> getStadiumOrders(String stadiumCode) {
        return ordersRepository.findByStadiumCode(stadiumCode);
    }

    @Override
    public List<Orders> getActivityOrdersWaitingDistributed(Integer activityId) {
        return ordersRepository.findByActivityIdAndSeatStatusAndStatus(activityId,0,1);
    }

    @Override
    public List<Orders> getInvalidOrders() {
        //设置过期时间
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MINUTE, -15);// 现在时刻向前15分钟
        Date deadline = c.getTime();
        return ordersRepository.findByStatusAndReserveDateBefore(0,deadline);
    }

    @Override
    public void setInvalidOrdersCanceled(Integer ordersId) {
        ordersRepository.setOrdersStatus(ordersId,-1);
    }

    @Override
    public void setSeatsForOrders(Integer ordersId, Integer first, Integer second, Integer third) {
        ordersRepository.setOrdersSeats(ordersId,first,second,third);
    }

    @Override
    public Orders createOrders(Orders orders, Coupon coupon) {
        Activity activity = activityService.getActivity(orders.getActivityId());
        //计算订单总价
        double sum;
        if(orders.getSeatStatus() == 1) //选座购买
            sum = orders.getFirstAmount() * activity.getFirstClassPrice() + orders.getSecondAmount() * activity.getSecondClassPrice() + orders.getThirdAmount() * activity.getThirdClassPrice();
        else //未选座购买
            sum = orders.getRandomAmount() * (activity.getFirstClassPrice() + activity.getSecondClassPrice() + activity.getThirdClassPrice())/3;

        sum *= 1 - memberService.getMemberLevel(orders.getMemberId()) * MEMBER_LEVEL_DISCOUNT;
        if(coupon != null) {
            sum *= coupon.getDiscount();
        }
        orders.setTotalPrice(sum);
        //在活动中减去对应预定的座位数
        if(orders.getSeatStatus() == 1) {
            activityService.deductSeats(orders.getActivityId(), 1, orders.getFirstAmount());
            activityService.deductSeats(orders.getActivityId(), 2, orders.getSecondAmount());
            activityService.deductSeats(orders.getActivityId(), 3, orders.getThirdAmount());
        }
        //设置订单预定时间
        orders.setReserveDate(new Date());
        return ordersRepository.save(orders);
    }

    @Override
    public Map<String,String> cancelOrders(Orders orders) {
        long between=(new Date().getTime() - orders.getPayDate().getTime())/1000;//除以1000是为了转换成秒
        long day=between/(24*3600);
        double rate = day < 3 ? 0.8 : 0.5;
        double back = orders.getTotalPrice() * rate;
        double currentBalance = memberService.returnBalance(orders.getMemberId(),back);
        ordersRepository.setOrdersStatus(orders.getId(),-1);
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
    public Map<String,String> payOrders(Orders orders) {
        //会员余额中扣钱
        double currentBalance = memberService.deductBalance(orders.getMemberId(),orders.getTotalPrice());
        //根据消费情况，增加积分
        Integer currentPoints = memberService.addPoints(orders.getMemberId(), (int) (orders.getTotalPrice()/MEMBER_POINT_RATE));
        //根据消费情况，升级等级
        Integer currentLevel = memberService.getMemberLevel(orders.getMemberId());
        if(currentLevel < MEMBER_MAX_LEVEL && orders.getTotalPrice() > MEMBER_UPGRADE_STANDARD)
            currentLevel = memberService.upgrade(orders.getMemberId());
        //若使用优惠券
        if(orders.getCouponId() != -1)
            couponService.useCoupon(orders.getCouponId());
        //将订单状态设置为已支付
        ordersRepository.setOrdersStatus(orders.getId(),1);
        //设置订单支付时间
        ordersRepository.setOrdersPayDate(orders.getId(), new Date());

        Map<String,String> result = new HashMap<>();
        result.put("会员余额", String.valueOf(currentBalance));
        result.put("会员积分", String.valueOf(currentPoints));
        result.put("会员等级", String.valueOf(currentLevel));
        return result;
    }

    @Override
    public Map<String,String> payOrders(Orders orders, String account) {
        //从支付账户的余额中扣钱
        double currentBalance = accountService.deductAccountBalance(account,orders.getTotalPrice());
        //根据消费情况，增加积分
        Integer currentPoints = memberService.addPoints(orders.getMemberId(), (int) (orders.getTotalPrice()/MEMBER_POINT_RATE));
        //根据消费情况，升级等级
        Integer currentLevel = memberService.getMemberLevel(orders.getMemberId());
        if(currentLevel < MEMBER_MAX_LEVEL && orders.getTotalPrice() > MEMBER_UPGRADE_STANDARD)
            currentLevel = memberService.upgrade(orders.getMemberId());
        //若使用优惠券
        if(orders.getCouponId() != -1)
            couponService.useCoupon(orders.getCouponId());
        //将订单状态设置为已支付
        ordersRepository.setOrdersStatus(orders.getId(),1);
        //设置订单支付时间
        ordersRepository.setOrdersPayDate(orders.getId(), new Date());

        Map<String,String> result = new HashMap<>();
        result.put("账户余额", String.valueOf(currentBalance));
        result.put("会员积分", String.valueOf(currentPoints));
        result.put("会员等级", String.valueOf(currentLevel));
        return result;
    }
}

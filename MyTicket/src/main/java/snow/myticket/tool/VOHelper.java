package snow.myticket.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Orders;
import snow.myticket.service.ActivityService;
import snow.myticket.service.StadiumService;
import snow.myticket.vo.ActivityVO;
import snow.myticket.vo.OrdersVO;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class VOHelper {
    private final ActivityService activityService;
    private final StadiumService stadiumService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public VOHelper(ActivityService activityService, StadiumService stadiumService) {
        this.activityService = activityService;
        this.stadiumService = stadiumService;
    }

    public OrdersVO ordersConvert(Orders orders){
        OrdersVO ordersVO = new OrdersVO();
        ordersVO.setId(orders.getId());
        ordersVO.setActivityName(activityService.getActivity(orders.getActivityId()).getTitle());
        ordersVO.setStadiumName(stadiumService.getStadium(orders.getStadiumCode()).getName());
        ordersVO.setTotalPrice(orders.getTotalPrice());
        ordersVO.setFirstAmount(orders.getFirstAmount());
        ordersVO.setSecondAmount(orders.getSecondAmount());
        ordersVO.setThirdAmount(orders.getThirdAmount());
        ordersVO.setRandomAmount(orders.getRandomAmount());

        String seatStatus;
        if(orders.getSeatStatus() == -1)
            seatStatus = "已分配座位";
        else if(orders.getSeatStatus() == 0)
            seatStatus = "未选座购买";
        else if(orders.getSeatStatus() == 1)
            seatStatus = "已选座购买";
        else
            seatStatus = "座位状态异常";
        ordersVO.setSeatStatus(seatStatus);

        String couponUseStatus;
        if(orders.getCouponId() == -1)
            couponUseStatus = "未使用优惠券";
        else
            couponUseStatus = "已使用优惠券";
        ordersVO.setCouponUseStatus(couponUseStatus);

        String payStatus;
        if(orders.getStatus() == -1)
            payStatus = "已取消";
        else if(orders.getStatus() == 0)
            payStatus = "待支付";
        else if(orders.getStatus() == 1)
            payStatus = "已支付";
        else if(orders.getStatus() == 3)
            payStatus = "已完成";
        else
            payStatus = "支付状态异常";
        ordersVO.setPayStatus(payStatus);

        ordersVO.setReserveDate(orders.getReserveDate().toString());

        //date转换为localDateTime
        Instant instant = orders.getReserveDate().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

        ordersVO.setExpireDate(localDateTime.plusMinutes(15).toString() + "+00:00");

        return ordersVO;
    }

    public ActivityVO activityConvert(Activity activity){
        ActivityVO activityVO = new ActivityVO();
        activityVO.setId(activity.getId());

        activityVO.setHoldDate(dateToString(activity.getHoldDate()));
        activityVO.setSellDate(dateToString(activity.getSellDate()));
        activityVO.setStadiumName(stadiumService.getStadium(activity.getStadiumCode()).getName());
        activityVO.setStadiumCode(activity.getStadiumCode());
        activityVO.setType(activity.getType());
        activityVO.setTitle(activity.getTitle());
        activityVO.setLocation(activity.getLocation());
        activityVO.setDescription(activity.getDescription());
        activityVO.setFirstClassPrice(activity.getFirstClassPrice());
        activityVO.setSecondClassPrice(activity.getSecondClassPrice());
        activityVO.setThirdClassPrice(activity.getThirdClassPrice());
        activityVO.setFirstClassSeats(activity.getFirstClassSeats());
        activityVO.setSecondClassSeats(activity.getSecondClassSeats());
        activityVO.setThirdClassSeats(activity.getThirdClassSeats());

        Date now = new Date();
        String sellStatus;
        if(now.before(activity.getSellDate()))
            sellStatus = "还未发售";
        else if(now.before(activity.getHoldDate()))
            sellStatus = "可以购票";
        else
            sellStatus = "结束发售";

        activityVO.setSellStatus(sellStatus);

        return activityVO;
    }

    private String dateToString(Date date){
        return sdf.format(date);
    }

}

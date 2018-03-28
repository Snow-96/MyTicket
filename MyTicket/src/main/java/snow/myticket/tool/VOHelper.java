package snow.myticket.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import snow.myticket.bean.Orders;
import snow.myticket.service.ActivityService;
import snow.myticket.service.StadiumService;
import snow.myticket.vo.OrdersVO;

@Component
public class VOHelper {
    private final ActivityService activityService;
    private final StadiumService stadiumService;

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

        return ordersVO;
    }

}

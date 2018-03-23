package snow.myticket.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import snow.myticket.bean.Orders;
import snow.myticket.service.ActivityService;
import snow.myticket.service.OrdersService;

import java.util.Date;
import java.util.List;

@Component
public class ScheduleHelper {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ActivityService activityService;
    private final static long SECOND = 1000;

    /**
     * 每隔10秒，检测订单支付情况
     */
    @Scheduled(fixedRate = SECOND * 10)
    public void checkOrders() {
        List<Orders> invalidOrders = ordersService.getInvalidOrders();
        for(Orders orders : invalidOrders){
            ordersService.setInvalidOrdersCanceled(orders.getId());
            if(orders.getSeatStatus() == 1) {
                activityService.addSeats(orders.getActivityId(), 1, orders.getFirstAmount());
                activityService.addSeats(orders.getActivityId(), 2, orders.getSecondAmount());
                activityService.addSeats(orders.getActivityId(), 3, orders.getThirdAmount());
            }
        }
    }
}

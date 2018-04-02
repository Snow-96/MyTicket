package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Orders;
import snow.myticket.repository.OrdersRepository;
import snow.myticket.service.*;

import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Orders getOrders(Integer orderId) {
        return ordersRepository.findById(orderId);
    }

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
        c.add(Calendar.MINUTE, -1);// 现在时刻向前1分钟
        Date deadline = c.getTime();
        return ordersRepository.findByStatusAndReserveDateBefore(0,deadline);
    }

    @Override
    public List<Orders> getFinishedOrders() {
        return ordersRepository.findByStatus(3);
    }

    @Override
    public boolean checkOrdersValid(Integer ordersId) {
        return ordersRepository.findById(ordersId).getStatus() != -1;
    }

    @Override
    public void setInvalidOrdersCanceled(Integer ordersId) {
        ordersRepository.setOrdersStatusByOrdersId(ordersId,-1);
    }

    @Override
    public void setActivityOrdersChecked(Integer activityId) {
        ordersRepository.setOrdersStatusByActivityId(activityId,3);
    }

    @Override
    public void setPlatformOrdersTransfer(Integer ordersId) {
        ordersRepository.setOrdersStatusByOrdersId(ordersId, 2);
    }

    @Override
    public void setSeatsForOrders(Integer ordersId, Integer first, Integer second, Integer third) {
        ordersRepository.setOrdersSeats(ordersId,first,second,third);
    }

    @Override
    public Orders createOrders(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public void cancelOrders(Orders orders) {
        ordersRepository.setOrdersStatusByOrdersId(orders.getId(),-1);
    }

    @Override
    public void payOrders(Integer ordersId, Date payDate) {
        ordersRepository.setOrdersStatusByOrdersId(ordersId,1);
        ordersRepository.setOrdersPayDate(ordersId,payDate);
    }


}

package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snow.myticket.bean.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Integer>{

    /**
     * 根据订单ID，获取该订单的座位列表
     * @param ordersId 订单ID
     * @return 座位列表
     */
    List<Seat> findByOrdersId(Integer ordersId);

    /**
     * 根据活动ID，获取该订单的座位列表
     * @param activityId 活动ID
     * @return 座位列表
     */
    List<Seat> findByActivityId(Integer activityId);

    /**
     * 根据订单ID，删除该订单的座位列表
     * @param ordersId 订单ID
     */
    void deleteByOrdersId(Integer ordersId);

}

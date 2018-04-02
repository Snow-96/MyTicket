package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import snow.myticket.bean.Orders;

import java.util.Date;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{

    /**
     * 根据订单ID，得到订单实体
     * @param ordersId 订单ID
     * @return 订单实体
     */
    Orders findById(Integer ordersId);

    /**
     * 根据订单状态，获取订单列表
     * @param status 订单状态
     * @return 订单列表
     */
    List<Orders> findByStatus(Integer status);

    /**
     * 根据会员ID获取订单列表
     * @param memberId 会员ID
     * @return 活动列表
     */
    List<Orders> findByMemberId(Integer memberId);

    /**
     * 根据场馆编码获取订单列表
     * @param stadiumCode 场馆编码
     * @return
     */
    List<Orders> findByStadiumCode(String stadiumCode);

    /**
     * 根据活动ID，座位分配状态，订单状态获取订单列表
     * @param activityId 活动ID
     * @param seatStatus 座位分配状态
     * @param status 订单状态
     * @return 订单列表
     */
    List<Orders> findByActivityIdAndSeatStatusAndStatus(Integer activityId, Integer seatStatus, Integer status);

    /**
     * 根据状态及时间轴获取订单列表
     * @param status 状态
     * @param deadline 过期时间
     * @return 订单列表
     */
    List<Orders> findByStatusAndReserveDateBefore(Integer status, Date deadline);

    /**
     * 根据状态及时间轴获取订单列表
     * @param status 状态
     * @param time 时间
     * @return 订单列表
     */
    List<Orders> findByStatusInAndPayDateAfterOrderByPayDateAsc(Integer[] status, Date time);

    /**
     * 设置订单状态
     * @param ordersId 订单ID
     * @param status 订单状态
     */
    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET o.status = ?2 WHERE o.id = ?1")
    void setOrdersStatusByOrdersId(Integer ordersId, Integer status);

    /**
     * 设置活动订单状态
     * @param activityId 活动ID
     * @param status 订单状态
     */
    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET o.status = ?2 WHERE o.activityId = ?1 and o.status = 1")
    void setOrdersStatusByActivityId(Integer activityId, Integer status);

    /**
     * 设置订单支付时间
     * @param ordersId 订单ID
     * @param payDate 支付时间
     */
    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET o.payDate = ?2 WHERE o.id = ?1")
    void setOrdersPayDate(Integer ordersId, Date payDate);

    /**
     * 为订单设置座位
     * @param ordersId 订单ID
     * @param first 一等座位数
     * @param second 二等座位数
     * @param third 三等座位数
     */
    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET o.firstAmount = ?2, o.secondAmount = ?3, o.thirdAmount = ?4, o.seatStatus = -1 WHERE o.id = ?1")
    void setOrdersSeats(Integer ordersId, Integer first, Integer second, Integer third);
}

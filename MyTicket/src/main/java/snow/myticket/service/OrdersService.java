package snow.myticket.service;

import snow.myticket.bean.Coupon;
import snow.myticket.bean.Orders;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrdersService {

    /**
     * 得到某一会员的全部订单信息
     * @param memberId 会员ID
     * @return 订单列表
     */
    List<Orders> getMemberOrders(Integer memberId);

    /**
     * 得到某一场馆的全部订单信息
     * @param stadiumCode 场馆编码
     * @return 订单列表
     */
    List<Orders> getStadiumOrders(String stadiumCode);

    /**
     * 得到某一活动所有待配票订单信息
     * @param activityId 活动ID
     * @return 订单列表
     */
    List<Orders> getActivityOrdersWaitingDistributed(Integer activityId);

    /**
     * 获取所有待支付且已超时的订单
     * @return 订单列表
     */
    List<Orders> getInvalidOrders();

    /**
     * 设置待支付且已超时订单为取消状态
     * @param ordersId 订单ID
     */
    void setInvalidOrdersCanceled(Integer ordersId);

    /**
     * 为订单分配座位
     * @param ordersId 订单ID
     * @param first 一等座位数
     * @param second 二等座位数
     * @param third 三等座位数
     */
    void setSeatsForOrders(Integer ordersId, Integer first, Integer second, Integer third);

    /**
     * 在DB中创建订单实体
     * @param orders 订单实体
     * @return 订单实体
     */
    Orders createOrders(Orders orders);

    /**
     * 取消订单
     * @param orders 订单实体
     */
    void cancelOrders(Orders orders);

    /**
     * 支付订单
     * @param ordersId 订单ID
     * @param payDate 支付时间
     */
    void payOrders(Integer ordersId, Date payDate);
}

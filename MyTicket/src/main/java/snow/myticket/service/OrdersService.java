package snow.myticket.service;

import snow.myticket.bean.Orders;

import java.util.Date;
import java.util.List;

public interface OrdersService {
    /**
     * 根据订单ID，获取订单实体
     * @param ordersId 订单ID
     * @return 订单实体
     */
    Orders getOrders(Integer ordersId);

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
     * 得到所有已完成订单
     * @return 订单列表
     */
    List<Orders> getFinishedOrders();

    /**
     * 根据订单状态，获取订单数量
     * @param status 订单状态
     * @return 订单数量
     */
    Integer getOrdersAmountByStatus(Integer status);

    /**
     * 判断订单有效性
     * @param ordersId 订单ID
     * @return 订单是否有效，true有效，false无效
     */
    boolean checkOrdersValid(Integer ordersId);

    /**
     * 设置待支付且已超时订单为取消状态
     * @param ordersId 订单ID
     */
    void setInvalidOrdersCanceled(Integer ordersId);

    /**
     * 设置活动订单为已检票
     * @param activityId 活动ID
     */
    void setActivityOrdersChecked(Integer activityId);

    /**
     * 根据订单ID，将订单设置为已打入场馆账户状态
     * @param ordersId 订单ID
     */
    void setPlatformOrdersTransfer(Integer ordersId);

    /**
     * 为订单分配座位,且设置订单座位状态为已分配
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

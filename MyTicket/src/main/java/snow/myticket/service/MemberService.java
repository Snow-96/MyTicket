package snow.myticket.service;

import snow.myticket.bean.Coupon;
import snow.myticket.bean.Member;
import snow.myticket.bean.Orders;

import java.util.Map;

public interface MemberService {
    /**
     * 根据邮箱获取会员实体
     * @param email 会员邮箱
     * @return 会员实体
     */
    Member getMember(String email);

    /**
     * 根据会员ID获取会员等级
     * @param memberId 会员ID
     * @return 会员等级
     */
    Integer getMemberLevel(Integer memberId);

    /**
     * 根据会员ID获取会员积分
     * @param memberId 会员ID
     * @return 会员积分
     */
    Integer getMemberPoints(Integer memberId);

    /**
     * 在DB中创建会员实体
     * @param member 会员实体
     */
    void createMember(Member member);

    /**
     * 取消会员资格/不可恢复
     * @param email 会员邮箱
     */
    void cancelMember(String email);

    /**
     * 修改会员信息
     * @param member 会员实体
     */
    void modifyMemberInfo(Member member);

    /**
     * 查看会员余额是否足够支付
     * @param memberId 会员ID
     * @param sum 待支付总金额
     * @return 是否足够
     */
    boolean checkMemberBalance(Integer memberId, Double sum);

    /**
     * 会员预定订单
     * @param orders 订单实体
     * @param coupon 优惠券实体
     * @return 订单实体
     */
    Orders reserveOrders(Orders orders, Coupon coupon);

    /**
     * 取消预定
     * @param orders 订单实体
     * @return map信息
     */
    Map<String,String> cancelOrders(Orders orders);

    /**
     * 通过会员余额支付订单
     * @param orders 订单实体
     * @return map信息
     */
    Map<String,String> payByMemberAccount(Orders orders);

    /**
     * 通过支付宝或网上银行支付订单
     * @param orders 订单实体
     * @param account 支付宝或网上银行账户
     * @return map信息
     */
    Map<String,String> payByExternalAccount(Orders orders, String account);

    /**
     * 会员升级
     * @param memberId 会员ID
     * @return 会员级别
     */
    Integer upgrade(Integer memberId);

    /**
     * 增加会员积分
     * @param memberId 会员ID
     * @param point 会员增加积分
     * @return 会员积分
     */
    Integer addPoints(Integer memberId, int point);

    /**
     * 扣除会员积分
     * @param memberId 会员ID
     * @param point 会员扣除积分
     * @return 会员积分
     */
    Integer deductPoints(Integer memberId, int point);

    /**
     * 从会员余额中扣钱
     * @param memberId 会员ID
     * @param sum 扣除总金额
     * @return 会员余额
     */
    Double deductBalance(Integer memberId, Double sum);

    /**
     * 向会员余额中退钱
     * @param memberId 会员ID
     * @param sum 退还总金额
     * @return 会员余额
     */
    Double returnBalance(Integer memberId, Double sum);
}

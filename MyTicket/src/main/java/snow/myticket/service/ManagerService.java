package snow.myticket.service;

import snow.myticket.bean.Manager;

import java.util.Map;

public interface ManagerService {

    /**
     * 根据管理员账号，获取管理员实体
     * @param account 账号
     * @return 管理员实体
     */
    Manager getManager(String account);

    /**
     * 通过场馆注册申请
     * @param stadiumId 场馆ID
     */
    void passStadiumApplication(Integer stadiumId);

    /**
     * 拒绝场馆注册申请
     * @param stadiumId 场馆ID
     */
    void rejectStadiumApplication(Integer stadiumId);

    /**
     * 通过场馆信息修改的申请
     * @param reviewId 审核ID
     */
    void passStadiumModifyInfo(Integer reviewId);

    /**
     * 拒绝场馆信息修改的申请
     * @param reviewId 审核ID
     */
    void rejectStadiumModifyInfo(Integer reviewId);

    /**
     * 根据订单ID，将相应订单设为已转帐状态，并结算金额
     * @param ordersId 订单ID
     * @return map信息
     */
    Map<String, String> ordersTransfer(Integer ordersId);
}

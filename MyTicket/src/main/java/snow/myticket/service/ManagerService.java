package snow.myticket.service;

import snow.myticket.bean.Review;

public interface ManagerService {

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
     * @param review 审核实体
     */
    void passStadiumModifyInfo(Review review);

    /**
     * 拒绝场馆信息修改的申请
     * @param reviewId 审核ID
     */
    void rejectStadiumModifyInfo(Integer reviewId);
}

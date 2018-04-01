package snow.myticket.service;

import snow.myticket.bean.Activity;
import snow.myticket.bean.Review;
import snow.myticket.bean.Stadium;

import java.util.Map;

public interface StadiumService {
    /**
     * 根据场馆编码获取场馆实体
     * @param stadiumCode 场馆编码
     * @return 场馆实体
     */
    Stadium getStadium(String stadiumCode);

    /**
     * 在DB中创建场馆实体（申请状态）
     * @param stadium 场馆实体
     */
    void createStadiumApply(Stadium stadium);

    /**
     * 查看修改状态
     * @param stadiumCode 场馆编码
     * @return 0代表审核中，NULL代表当前没有修改申请
     */
    Integer checkModifyStatus(String stadiumCode);

    /**
     * 提交要修改的场馆信息
     * @param stadium 场馆实体
     */
    void modifyStadiumInfo(Stadium stadium);

    /**
     * 设置场馆注册申请有效
     * @param stadiumCode 场馆编码
     * @param stadiumId 场馆ID
     */
    void setStadiumApplicationValid(String stadiumCode, Integer stadiumId);

    /**
     * 设置场馆注册申请无效
     * @param stadiumId 场馆ID
     */
    void setStadiumApplicationInvalid(Integer stadiumId);

    /**
     * 设置场馆信息修改的申请为有效
     * @param review 审核实体
     */
    void setStadiumModifyInfoValid(Review review);

    /**
     * 设置场馆信息修改的申请为无效
     * @param reviewId 审核ID
     */
    void setStadiumModifyInfoInvalid(Integer reviewId);

    /**
     * 发布一个活动
     * @param activity 活动实体
     */
    void releaseActivity(Activity activity);

    /**
     * 为活动配票
     * @param activityId 活动ID
     * @return map信息
     */
    Map<String,String> distributeTicketsForActivity(Integer activityId);

    /**
     * 为活动检票
     * @param activityId 活动ID
     */
    void checkTicketsForActivity(Integer activityId);
}

package snow.myticket.service;

import snow.myticket.bean.Activity;
import snow.myticket.bean.Seat;

import java.util.Date;
import java.util.List;

public interface ActivityService {
    /**
     * 在DB中创建活动实体
     * @param activity 活动实体
     */
    void createActivity(Activity activity);

    /**
     * 根据活动ID，获取活动实体
     * @param activityId 活动ID
     * @return 活动实体
     */
    Activity getActivity(Integer activityId);

    /**
     * 根据活动ID，获取活动的座位列表
     * @param activityId 活动ID
     * @return 座位列表
     */
    List<Seat> getActivitySeat(Integer activityId);

    /**
     * 获取所有活动
     * @return 活动列表
     */
    List<Activity> getAllActivities();

    /**
     * 得到某一场馆所有待检票的活动
     * @param stadiumCode 场馆编码
     * @return 活动列表
     */
    List<Activity> getAllActivitiesNeedChecked(String stadiumCode);

    /**
     * 得到某一场馆所有待配票的活动
     * @param stadiumCode 场馆编码
     * @return 活动列表
     */
    List<Activity> getAllActivitiesNeedDistributed(String stadiumCode);

    /**
     * 根据场馆编码获取场馆举办的所有活动
     * @param stadiumCode 场馆编码
     * @return 活动列表
     */
    List<Activity> getActivitiesByStadiumCode(String stadiumCode);

    /**
     * 设置活动为已检票
     * @param activityId 活动ID
     */
    void setActivityChecked(Integer activityId);

    /**
     * 设置活动为已配票
     * @param activityId 活动ID
     */
    void setActivityDistributed(Integer activityId);

    /**
     * 增加座位
     * @param activityId 活动ID
     * @param seatClass 座位等级
     * @param amount 数量
     */
    void addSeats(Integer activityId, Integer seatClass ,Integer amount);

    /**
     * 减少座位
     * @param activityId 活动ID
     * @param seatClass 座位等级
     * @param amount 数量
     */
    void deductSeats(Integer activityId, Integer seatClass ,Integer amount);
}

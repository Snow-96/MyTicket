package snow.myticket.service;

import snow.myticket.bean.Activity;

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
     * 根据场馆编码获取场馆举办的所有活动
     * @param stadiumCode 场馆编码
     * @return 活动列表
     */
    List<Activity> getActivitiesByStadiumCode(String stadiumCode);

    /**
     * 根据举办时间范围，获取所有的活动
     * @param start 起始时间
     * @param end 结束时间
     * @return 活动列表
     */
    List<Activity> getActivitiesByDate(Date start, Date end);

    /**
     * 根据类型，获取所有活动
     * @param type 类型
     * @return 活动列表
     */
    List<Activity> getActivitiesByType(String type);

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

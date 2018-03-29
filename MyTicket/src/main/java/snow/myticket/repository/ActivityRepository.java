package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import snow.myticket.bean.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Integer>{

    /**
     * 得到全部活动
     * @return 活动列表
     */
    List<Activity> findAll();

    /**
     * 根据条件过滤活动
     * @param stadiumCode 场馆编号
     * @return 活动列表
     */
    List<Activity> findByStadiumCode(String stadiumCode);

    /**
     * 根据条件过滤活动
     * @param start 开始时间
     * @param end 结束
     * @return 活动列表
     */
    List<Activity> findByHoldDateBetween(Date start, Date end);

    /**
     * 根据条件过滤活动
     * @param type 类型
     * @return 活动列表
     */
    List<Activity> findByType(String type);

    /**
     * 根据活动ID得到活动
     * @param activityId 活动ID
     * @return 活动实体
     */
    Activity findById(Integer activityId);

    /**
     * 增加一等座位数量
     * @param activityId 活动ID
     * @param amount 数量
     */
    @Transactional
    @Modifying
    @Query("UPDATE Activity a SET a.firstClassSeats = a.firstClassSeats + ?2 WHERE a.id = ?1")
    void addFirstClassSeats(Integer activityId, Integer amount);

    /**
     * 增加二等座位数量
     * @param activityId 活动ID
     * @param amount 数量
     */
    @Transactional
    @Modifying
    @Query("UPDATE Activity a SET a.secondClassSeats = a.secondClassSeats + ?2 WHERE a.id = ?1")
    void addSecondClassSeats(Integer activityId, Integer amount);

    /**
     * 增加三等座位数量
     * @param activityId 活动ID
     * @param amount 数量
     */
    @Transactional
    @Modifying
    @Query("UPDATE Activity a SET a.thirdClassSeats = a.thirdClassSeats + ?2 WHERE a.id = ?1")
    void addThirdClassSeats(Integer activityId, Integer amount);

    /**
     * 减少一等座位数量
     * @param activityId 活动ID
     * @param amount 数量
     */
    @Transactional
    @Modifying
    @Query("UPDATE Activity a SET a.firstClassSeats = a.firstClassSeats - ?2 WHERE a.id = ?1")
    void deductFirstClassSeats(Integer activityId, Integer amount);

    /**
     * 减少二等座位数量
     * @param activityId 活动ID
     * @param amount 数量
     */
    @Transactional
    @Modifying
    @Query("UPDATE Activity a SET a.secondClassSeats = a.secondClassSeats - ?2 WHERE a.id = ?1")
    void deductSecondClassSeats(Integer activityId, Integer amount);

    /**
     * 减少三等座位数量
     * @param activityId 活动ID
     * @param amount 数量
     */
    @Transactional
    @Modifying
    @Query("UPDATE Activity a SET a.thirdClassSeats = a.thirdClassSeats - ?2 WHERE a.id = ?1")
    void deductThirdClassSeats(Integer activityId, Integer amount);
}

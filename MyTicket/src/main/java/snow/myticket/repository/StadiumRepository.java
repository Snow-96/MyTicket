package snow.myticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import snow.myticket.bean.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium,Integer> {

    /**
     * 通过场馆编码查找场馆实体
     * @param stadiumCode 场馆编码
     * @return 场馆实体
     */
    Stadium findByCode(String stadiumCode);

    /**
     * 设置场馆注册申请有效
     * @param stadiumCode 场馆编码
     * @param stadiumId 场馆ID
     */
    @Transactional
    @Modifying
    @Query("UPDATE Stadium s SET s.status = 1, s.code = ?1 WHERE s.id = ?2")
    void setStadiumApplicationValid(String stadiumCode, Integer stadiumId);

    /**
     * 设置场馆注册申请无效
     * @param stadiumId 场馆ID
     */
    @Transactional
    @Modifying
    @Query("UPDATE Stadium s SET s.status = -1 WHERE s.id = ?1")
    void setStadiumApplicationInvalid(Integer stadiumId);

    /**
     * 修改场馆信息
     * @param location 位置
     * @param seatAmount 座位数量
     * @param stadiumCode 场馆编码
     */
    @Transactional
    @Modifying
    @Query("UPDATE Stadium s SET s.location = ?1, s.seatAmount = ?2 WHERE s.code = ?3")
    void modifyStadiumInfo(String location, Integer seatAmount, String stadiumCode);
}

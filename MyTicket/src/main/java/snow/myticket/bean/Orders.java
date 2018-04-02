package snow.myticket.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer memberId;
    private Integer activityId;
    /**
     * 若不使用则为-1
     */
    private Integer couponId;
    private String stadiumCode;
    private Double totalPrice;
    /**
     * -1代表已分配座位，0代表未选座购买，1代表选择座位购买
     */
    private Integer seatStatus;
    private Integer firstAmount;
    private Integer secondAmount;
    private Integer thirdAmount;
    /**
     * 未选座购买总座位数
     */
    private Integer randomAmount;
    /**
     * 0为预定状态，1为已支付，2为已将收入打入场馆账户，-1为已取消, 3为已检票即已完成
     */
    private Integer status;
    private Date reserveDate;
    private Date payDate;

    public Orders() {
    }

    public Orders(Integer memberId, Integer activityId, String stadiumCode, Double totalPrice, Integer firstAmount, Integer secondAmount, Integer thirdAmount) {
        this.memberId = memberId;
        this.activityId = activityId;
        this.stadiumCode = stadiumCode;
        this.totalPrice = totalPrice;
        this.firstAmount = firstAmount;
        this.secondAmount = secondAmount;
        this.thirdAmount = thirdAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getStadiumCode() {
        return stadiumCode;
    }

    public void setStadiumCode(String stadiumCode) {
        this.stadiumCode = stadiumCode;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd-HH-mm-ss")
    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd-HH-mm-ss")
    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Integer getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(Integer seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Integer getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(Integer firstAmount) {
        this.firstAmount = firstAmount;
    }

    public Integer getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(Integer secondAmount) {
        this.secondAmount = secondAmount;
    }

    public Integer getThirdAmount() {
        return thirdAmount;
    }

    public void setThirdAmount(Integer thirdAmount) {
        this.thirdAmount = thirdAmount;
    }

    public Integer getRandomAmount() {
        return randomAmount;
    }

    public void setRandomAmount(Integer randomAmount) {
        this.randomAmount = randomAmount;
    }
}

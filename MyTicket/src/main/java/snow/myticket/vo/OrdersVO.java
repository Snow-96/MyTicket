package snow.myticket.vo;

public class OrdersVO {
    private Integer id;
    private String activityName;
    private String stadiumName;
    private Double totalPrice;
    private Integer firstAmount;
    private Integer secondAmount;
    private Integer thirdAmount;
    private Integer randomAmount;
    private String couponUseStatus;
    private String seatStatus;
    private String payStatus;
    private String reserveDate;
    private String expireDate;
    private String seatInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getCouponUseStatus() {
        return couponUseStatus;
    }

    public void setCouponUseStatus(String couponUseStatus) {
        this.couponUseStatus = couponUseStatus;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }
}

package snow.myticket.vo;

public class OffLineOrdersVO {
    private String memberEmail;
    private Integer activityId;
    private String stadiumCode;
    private Double totalPrice;
    private Integer firstAmount;
    private Integer secondAmount;
    private Integer thirdAmount;

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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
}

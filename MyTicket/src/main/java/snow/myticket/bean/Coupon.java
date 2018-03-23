package snow.myticket.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Coupon {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer memberId;
    /**
     * 0代表有效，-1代表失效
     */
    private Integer status;
    private Integer needPoints;
    private Double discount;
    private Date expirationDate;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNeedPoints() {
        return needPoints;
    }

    public void setNeedPoints(Integer needPoints) {
        this.needPoints = needPoints;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

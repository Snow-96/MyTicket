package snow.myticket.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Integer id;
    private Date holdDate;
    private Date sellDate;
    private String stadiumCode;
    private String type;
    private String title;
    private String location;
    private String description;
    private Double firstClassPrice;
    private Double secondClassPrice;
    private Double thirdClassPrice;
    private Integer firstClassSeats;
    private Integer secondClassSeats;
    private Integer thirdClassSeats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public String getStadiumCode() {
        return stadiumCode;
    }

    public void setStadiumCode(String stadiumCode) {
        this.stadiumCode = stadiumCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFirstClassPrice() {
        return firstClassPrice;
    }

    public void setFirstClassPrice(Double firstClassPrice) {
        this.firstClassPrice = firstClassPrice;
    }

    public Double getSecondClassPrice() {
        return secondClassPrice;
    }

    public void setSecondClassPrice(Double secondClassPrice) {
        this.secondClassPrice = secondClassPrice;
    }

    public Double getThirdClassPrice() {
        return thirdClassPrice;
    }

    public void setThirdClassPrice(Double thirdClassPrice) {
        this.thirdClassPrice = thirdClassPrice;
    }

    public Integer getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(Integer firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public Integer getSecondClassSeats() {
        return secondClassSeats;
    }

    public void setSecondClassSeats(Integer secondClassSeats) {
        this.secondClassSeats = secondClassSeats;
    }

    public Integer getThirdClassSeats() {
        return thirdClassSeats;
    }

    public void setThirdClassSeats(Integer thirdClassSeats) {
        this.thirdClassSeats = thirdClassSeats;
    }
}

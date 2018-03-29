package snow.myticket.vo;

public class ActivityVO {
    private Integer id;
    private String holdDate;
    private String sellDate;
    private String stadiumName;
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
    private String sellStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
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

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }
}

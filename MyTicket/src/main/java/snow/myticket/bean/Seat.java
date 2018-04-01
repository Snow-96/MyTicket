package snow.myticket.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Seat {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer ordersId;
    private Integer activityId;
    private Integer row;
    private Integer col;
    private Integer seatLevel;

    public Seat() {
    }

    public Seat(Integer ordersId, Integer activityId, Integer row, Integer col, Integer seatLevel) {
        this.ordersId = ordersId;
        this.activityId = activityId;
        this.row = row;
        this.col = col;
        this.seatLevel = seatLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getSeatLevel() {
        return seatLevel;
    }

    public void setSeatLevel(Integer seatLevel) {
        this.seatLevel = seatLevel;
    }
}

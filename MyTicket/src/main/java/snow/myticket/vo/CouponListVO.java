package snow.myticket.vo;

import java.util.ArrayList;
import java.util.List;

public class CouponListVO {
    private Integer memberId;
    private Integer needPoints_1st;
    private Integer needPoints_2nd;
    private Integer needPoints_3rd;
    private Double discount_1st;
    private Double discount_2nd;
    private Double discount_3rd;
    private Integer amount_1st;
    private Integer amount_2nd;
    private Integer amount_3rd;
    private List<Integer> id_1st;
    private List<Integer> id_2nd;
    private List<Integer> id_3rd;

    public CouponListVO() {
    }

    public CouponListVO(Integer memberId, Integer amount_1st, Integer amount_2nd, Integer amount_3rd) {
        this.memberId = memberId;
        this.amount_1st = amount_1st;
        this.amount_2nd = amount_2nd;
        this.amount_3rd = amount_3rd;
        this.id_1st = new ArrayList<>();
        this.id_2nd = new ArrayList<>();
        this.id_3rd = new ArrayList<>();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getNeedPoints_1st() {
        return needPoints_1st;
    }

    public void setNeedPoints_1st(Integer needPoints_1st) {
        this.needPoints_1st = needPoints_1st;
    }

    public Integer getNeedPoints_2nd() {
        return needPoints_2nd;
    }

    public void setNeedPoints_2nd(Integer needPoints_2nd) {
        this.needPoints_2nd = needPoints_2nd;
    }

    public Integer getNeedPoints_3rd() {
        return needPoints_3rd;
    }

    public void setNeedPoints_3rd(Integer needPoints_3rd) {
        this.needPoints_3rd = needPoints_3rd;
    }

    public Double getDiscount_1st() {
        return discount_1st;
    }

    public void setDiscount_1st(Double discount_1st) {
        this.discount_1st = discount_1st;
    }

    public Double getDiscount_2nd() {
        return discount_2nd;
    }

    public void setDiscount_2nd(Double discount_2nd) {
        this.discount_2nd = discount_2nd;
    }

    public Double getDiscount_3rd() {
        return discount_3rd;
    }

    public void setDiscount_3rd(Double discount_3rd) {
        this.discount_3rd = discount_3rd;
    }

    public Integer getAmount_1st() {
        return amount_1st;
    }

    public void setAmount_1st(Integer amount_1st) {
        this.amount_1st = amount_1st;
    }

    public Integer getAmount_2nd() {
        return amount_2nd;
    }

    public void setAmount_2nd(Integer amount_2nd) {
        this.amount_2nd = amount_2nd;
    }

    public Integer getAmount_3rd() {
        return amount_3rd;
    }

    public void setAmount_3rd(Integer amount_3rd) {
        this.amount_3rd = amount_3rd;
    }

    public List<Integer> getId_1st() {
        return id_1st;
    }

    public void setId_1st(List<Integer> id_1st) {
        this.id_1st = id_1st;
    }

    public List<Integer> getId_2nd() {
        return id_2nd;
    }

    public void setId_2nd(List<Integer> id_2nd) {
        this.id_2nd = id_2nd;
    }

    public List<Integer> getId_3rd() {
        return id_3rd;
    }

    public void setId_3rd(List<Integer> id_3rd) {
        this.id_3rd = id_3rd;
    }
}

package snow.myticket.vo;

public class SeatVO {
    private Integer[][] firstSeat;
    private Integer[][] secondSeat;
    private Integer[][] thirdSeat;

    public SeatVO(Integer row, Integer col_1, Integer col_2, Integer col_3) {
        this.firstSeat = new Integer[row][col_1];
        this.secondSeat = new Integer[row][col_2];
        this.thirdSeat = new Integer[row][col_3];
    }

    public Integer[][] getFirstSeat() {
        return firstSeat;
    }

    public void setFirstSeat(Integer[][] firstSeat) {
        this.firstSeat = firstSeat;
    }

    public Integer[][] getSecondSeat() {
        return secondSeat;
    }

    public void setSecondSeat(Integer[][] secondSeat) {
        this.secondSeat = secondSeat;
    }

    public Integer[][] getThirdSeat() {
        return thirdSeat;
    }

    public void setThirdSeat(Integer[][] thirdSeat) {
        this.thirdSeat = thirdSeat;
    }
}

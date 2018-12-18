package jason.luo.domain;

import org.apache.ibatis.type.Alias;

@Alias(value = "movie_seat")
public class Seat {
    private int seatId;
    private int hallId;
    private int seatRow;
    private int seatCol;
    private boolean seatIsactive;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(int seatCol) {
        this.seatCol = seatCol;
    }

    public boolean isSeatIsactive() {
        return seatIsactive;
    }

    public void setSeatIsactive(boolean seatIsactive) {
        this.seatIsactive = seatIsactive;
    }
}

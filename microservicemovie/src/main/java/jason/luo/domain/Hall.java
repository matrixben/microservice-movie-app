package jason.luo.domain;

import org.apache.ibatis.type.Alias;

@Alias(value = "movie_hall")
public class Hall {
    private int hallId;
    private int seatNumber;
    private String description;

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

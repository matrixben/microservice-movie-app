package jason.luo.domain;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

@Alias(value = "movie_schedule")
public class MovieSchedule {
    private int scheduleId;
    private int movieId;
    private int hallId;
    private BigDecimal schedulePrice;
    private Date scheduleBegindatetime;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public BigDecimal getSchedulePrice() {
        return schedulePrice;
    }

    public void setSchedulePrice(BigDecimal schedulePrice) {
        this.schedulePrice = schedulePrice;
    }

    public Date getScheduleBegindatetime() {
        return scheduleBegindatetime;
    }

    public void setScheduleBegindatetime(Date scheduleBegindatetime) {
        this.scheduleBegindatetime = scheduleBegindatetime;
    }
}

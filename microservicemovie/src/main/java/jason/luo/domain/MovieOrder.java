package jason.luo.domain;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

@Alias(value = "movie_order")
public class MovieOrder {
    private int orderId;
    private int userId;
    private int scheduleId;
    private int seatNumber;
    private BigDecimal totalPrice;
    private Date buyDatetime;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getBuyDatetime() {
        return buyDatetime;
    }

    public void setBuyDatetime(Date buyDatetime) {
        this.buyDatetime = buyDatetime;
    }
}

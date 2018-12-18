package jason.luo.domain;

import org.apache.ibatis.type.Alias;

@Alias(value = "order_seat")
public class OrderSeat {
    private int orderId;
    private int seatId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}

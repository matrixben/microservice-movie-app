package jason.luo.dao;

import jason.luo.domain.Movie;
import jason.luo.domain.MovieOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MovieDao {
    Movie getMovie(int id);

    List<Movie> getAllMovies();

    BigDecimal getSchedulePrice(int scheduleId);

    /** 检查指定拍片的对应座位已经被订的数量，0则没有被占，多个入参要指定参数别名*/
    int checkSeats(@Param("scheduleId") int scheduleId, @Param("array") Integer[] seatsId);

    int createOrder(MovieOrder movieOrder);

    void bookSeat(@Param("orderId") int orderId, @Param("seatId") int seatId);
}

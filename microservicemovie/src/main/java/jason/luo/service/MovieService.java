package jason.luo.service;

import jason.luo.dao.MovieDao;
import jason.luo.domain.Movie;
import jason.luo.domain.MovieOrder;
import jason.luo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private RedisTemplate redisTemplate;

    private Jedis jedis;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static String ORDER_LIST_KEY = "order_list";
    private final static String SEAT_LIST_KEY = "seat_list";

    public User findUserById(int id) {
        return userFeignClient.findUserById(id);
    }

    public Movie getMovie(int id) {
        return movieDao.getMovie(id);
    }

    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    /** 返回订单id，传统的下单流程*/
    @Transactional
    public int orderSeats(int userId, int scheduleId, int[] seatsId) {
        Integer[] seatsId_Int = Arrays.stream(seatsId).boxed().toArray(Integer[]::new);
        //查询用户折扣
        BigDecimal discount = userFeignClient.getDiscountByUserId(userId);
        //查询此排片的单价
        BigDecimal unitPrice = movieDao.getSchedulePrice(scheduleId);
        BigDecimal totPrice = unitPrice.multiply(discount.add(BigDecimal.ONE));
        int orderId = -1;
        synchronized (this){
            //查询此排片的选中座位是否已经被订了
            if (movieDao.checkSeats(scheduleId, seatsId_Int) != 0){
                return -1;
            }
            //创建新的订单 insert
            MovieOrder newOrder = this.initMovieOrder(userId, scheduleId, seatsId.length, totPrice);
            orderId = insertOneOrderAndItsSeats(newOrder, seatsId_Int);
        }
        return orderId;
    }

    /** 初始化订单类*/
    private MovieOrder initMovieOrder(int userId, int scheduleId, int seatQty, BigDecimal totPrice){
        MovieOrder movieOrder = new MovieOrder();
        movieOrder.setUserId(userId);
        movieOrder.setScheduleId(scheduleId);
        movieOrder.setSeatNumber(seatQty);
        movieOrder.setTotalPrice(totPrice);
//        movieOrder.setBuyDatetime(new Date());由数据库生成当前时间
        return movieOrder;
    }

    /** 下单成功返回1，失败返回0，使用redis和lua脚本*/
    public int orderSeatsByRedis(int userId, int scheduleId, int[] seatsId){
        //不需要先加载所有排片和座位信息，因为入参数据都是准确的，从postgres数据库查出来的
        //查询用户折扣
        BigDecimal discount = userFeignClient.getDiscountByUserId(userId);
        //查询此排片的单价
        BigDecimal unitPrice = movieDao.getSchedulePrice(scheduleId);
        BigDecimal totPrice = unitPrice.multiply(discount.add(BigDecimal.ONE));
        //当前日期

        String dateStr = dateFormat.format(new Date());
        //开始redis查询
        //movie_order (order_id, user_id, schedule_id, seat_number, total_price, buy_datetime)
        //KEYS[1] = "user_" + scheduleId
        StringBuffer sb = new StringBuffer();
        System.out.println("seatsId.length: "+seatsId.length);
        for (int seat : seatsId) {
            sb.append(seat).append(",");
        }
        String str_seats = sb.toString().substring(0,sb.length()-1);
        System.out.println("seatId: "+str_seats);
        String orderScript =
            //先判断指定排片的指定座位是否已经存在
            "local table_seats = {} \n" +
            "string.gsub(ARGV[1], '[^,$]+',function (w) table.insert(table_seats,w) end) \n" +
            "for k,v in pairs(table_seats) do \n" +
            "    local hasSeat = redis.call('sismember', KEYS[2], v) \n" +
            "    if hasSeat == 1 then return 0 \n" +
            "    end \n" +
            "end \n" +
            "for k,v in pairs(table_seats) do \n" +
            "    redis.call('sadd', KEYS[2], v) \n" +
            "end \n" +
            //添加order_list，不用order_id，列表下标就是id
            "local order_detail = ''..KEYS[1]..','..KEYS[2]..','..#table_seats..','..ARGV[2]..','..ARGV[3] \n" +
            "redis.call('rpush', ARGV[4], order_detail) \n" +
            "redis.call('rpush', ARGV[5], ARGV[1]) \n" +
            "return 1";
        jedis = initJedisConnection();
        String sha1 = jedis.scriptLoad(orderScript);

        //第一个是集合set，key是schedule_id的字符串, value是seat_id的字符串
        //第二个是order_list列表，使用逗号分割拼接字符串存储order
        //第三个是seat_list列表，存储此order的所有seat_id
        return (int) jedis.evalsha(sha1, 2, ""+userId, "scheduleId_"+scheduleId,
                str_seats, ""+totPrice, dateStr, ORDER_LIST_KEY, SEAT_LIST_KEY);
    }

    private List<MovieOrder> getOrdersFromRedis(int maxSize) throws ParseException {
        //每次只读取一部分数据以避免内存占用过多，每次导入任务完成后都会清空redis数据
        jedis = initJedisConnection();
        List<String> orderListStr = jedis.lrange(ORDER_LIST_KEY, 0, maxSize);
        List<MovieOrder> orderList = new ArrayList<>(orderListStr.size());
        for (String s : orderListStr) {
            String[] fields = s.split(",");
            MovieOrder order = new MovieOrder();
            order.setUserId(Integer.parseInt(fields[0]));
            order.setScheduleId(Integer.parseInt(fields[1].split("_")[1]));
            order.setSeatNumber(Integer.parseInt(fields[2]));
            order.setTotalPrice(BigDecimal.valueOf(Double.valueOf(fields[3])));
            order.setBuyDatetime(dateFormat.parse(fields[4]));
            orderList.add(order);
        }
        return orderList;
    }
    private List<List<Integer>> getOrderSeatsFromRedis(int maxSize){
        jedis = initJedisConnection();
        List<String> orderSeatsStr = jedis.lrange(SEAT_LIST_KEY, 0, maxSize);
        List<List<Integer>> orderSeatsList = new ArrayList<>(orderSeatsStr.size());
        for (String s : orderSeatsStr) {
            String[] seats = s.split(",");
            List<Integer> seatsId = new ArrayList<>();
            for (String seat: seats) {
                seatsId.add(Integer.parseInt(seat));
            }
            orderSeatsList.add(seatsId);
        }
        return orderSeatsList;
    }

    /**返回成功插入的条数 */
    private int insertOrdersAndSeats(List<MovieOrder> orderList, List<List<Integer>> orderSeatsList){
        if (orderList.size() != orderSeatsList.size()){
            return 0;
        }
        for (int i = 0; i < orderList.size(); i++){
            if (0 == insertOneOrderAndItsSeats(orderList.get(i), orderSeatsList.get(i).toArray(new Integer[0]))){
                return i;
            }
        }
        return orderList.size();
    }

    @Transactional
    private int insertOneOrderAndItsSeats(MovieOrder movieOrder, Integer[] seatsId){
        int orderId;
        //insert语句只会返回影响行数，selectKey的返回id放在入参的对象里的同名属性
        movieDao.createOrder(movieOrder);
        orderId = movieOrder.getOrderId();
        //将选中座位关联到此订单 insert
        for (int seatId : seatsId) {
            movieDao.bookSeat(orderId, seatId);
        }
        return orderId;
    }

    /** 把redis的订单数据导入到postgres，然后清空redis*/
    public void importOrdersFromRedisToPostgres(int maxSize) throws ParseException {
        jedis = initJedisConnection();
        while (jedis.llen(ORDER_LIST_KEY) > 0){
            //查询movie_order表的数据并按倒序放入list<MovieOrder>
            List<MovieOrder> orderList = getOrdersFromRedis(maxSize);
            //查询order_seat表的数据并按倒序(只是为了后续删除时提高性能)放入list<OrderSeat>
            List<List<Integer>> orderSeatsList = getOrderSeatsFromRedis(maxSize);
            //新建事务方法:插入一条order和对应的seat
            //遍历调用此方法(非事务)
            int orderNum = insertOrdersAndSeats(orderList, orderSeatsList);
            //清空已插入的redis数据
            jedis.ltrim(ORDER_LIST_KEY, orderNum-1, -1);
        }
    }

    public void redisSetValue(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public Object redisGetValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Object redisGetValueByLua(String key){
        String luaScript = "local str = redis.call('get', KEYS[1]) \n" +
                "return str \n";
        jedis = initJedisConnection();
        String sha1 = jedis.scriptLoad(luaScript);
        //执行lua脚本
        return jedis.evalsha(sha1, 1, key);
    }

    public Object redisSetValueByLua(String key, String value){
        //lua脚本的字符串序列化和redisTemplate的字符串序列化不一样
        String luaScript = "redis.call('set', KEYS[1], ARGV[1]) \n" +
                "return 1 \n";
        jedis = initJedisConnection();
        String sha1 = jedis.scriptLoad(luaScript);
        //执行lua脚本
        return jedis.evalsha(sha1, 1, key, value);
    }

    public String redisSetValueByJedis(String key, String value){
        jedis = initJedisConnection();
        return jedis.set(key, value);
    }

    public String redisGetValueByJedis(String key){
        jedis = initJedisConnection();
        return jedis.get(key);
    }

    private Jedis initJedisConnection(){
        if (jedis != null){
            return jedis;
        }
        return (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
    }
}

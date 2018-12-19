package jason.luo.controller;

import jason.luo.domain.User;
import jason.luo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MovieApiController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable int id){
        //使用负载均衡ribbon后就可以用虚拟主机名替换ip地址,虚拟主机名中不能有下划线_
        return this.movieService.findUserById(id);
    }

    @GetMapping("/redistest/set")
    public String redisSet(String key, String value){
        this.movieService.redisSetValue(key, value);
        return "ok";
    }

    @GetMapping("/redistest/get/{str}")
    public String redisGet(@PathVariable String str){
        return (String) this.movieService.redisGetValue(str);
    }

    @GetMapping("/redistest/lua/get/{str}")
    public String redisLuaGet(@PathVariable String str){
        return (String) movieService.redisGetValueByLua(str);
    }

    @GetMapping("/redistest/lua/set")
    public Long redisLuaSet(String key, String value){
        return (Long) movieService.redisSetValueByLua(key, value);
    }

    @GetMapping("/redistest/jedis/get/{str}")
    public String jedisGet(@PathVariable String str){
        return (String) movieService.redisGetValueByJedis(str);
    }

    @GetMapping("/redistest/jedis/set")
    public String jedisSet(String key, String value){
        return movieService.redisSetValueByJedis(key, value);
    }

    @GetMapping("/redistest/order/set")
    public long orderSet(int userId, int scheduleId, int[] seatsId){
        return (long) movieService.orderSeatsByRedis(userId,scheduleId,seatsId);
    }

    @GetMapping("/testorder")
    public ModelAndView testPage(){
        return new ModelAndView("testpage");
    }

    /**
     * https://bbs.csdn.net/topics/391006697
     * 票务系统并发问题解决方法: 内存中维护两个已售和未售的集合
     * 对集合的访问加锁即可，绝不能查数据库来做判断
     * 改用redis
     * http://127.0.0.1:8082/sendorder?userId=1&scheduleId=1&seatsId=6,7
     * @param userId
     * @param scheduleId
     * @param seatsId
     * @return 订单id，-1表示下单失败
     */
    @PostMapping("/sendorder")
    public int orderTickets(int userId, int scheduleId, int[] seatsId){
        int orderId = 0;
        //传统查询关系型数据库的方式无法准确处理高并发业务
        orderId = movieService.orderSeatsByRedis(userId, scheduleId, seatsId);
        //改用redis创建订单时,无法获得当前订单id,所以先自行创建id用于排序(或者直接使用有序列表)
        //在之后同步到postgres时再按id顺序插入
        //如果完全使用redis进行订单的查询和插入操作，则在同步到数据库之前，所有订单影响的数据都只能查redis
        //而不能查postgres,比如余额不应该在用户表
        return orderId;
    }
}

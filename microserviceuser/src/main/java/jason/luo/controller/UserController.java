package jason.luo.controller;

import jason.luo.dao.UserDao;
import jason.luo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据库应该隐藏在api后面，其他服务只能通过api获取相应的数据
 */
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable int id){
        return this.userDao.getUser(id);
    }

    @GetMapping("/user/name/{name}")
    public User findUserByName(@PathVariable String name){
        return this.userDao.getUserByName(name);
    }

    @GetMapping("/user/class/{name}")
    public List<User> findUserByClassName(@PathVariable String name){
        return this.userDao.getUserByClassName(name);
    }

    @GetMapping("/user/discountbyuserid/{id}")
    public BigDecimal getDiscountByUserId(@PathVariable int id){
        return this.userDao.getDiscountByUserId(id);
    }

    @GetMapping("/hi")
    public String hello(){
        return "Welcome to the Eureka world.";
    }
}

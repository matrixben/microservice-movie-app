package jason.luo.service;

import jason.luo.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

/**
 * 使用负载均衡ribbon后就可以用虚拟主机名替换ip地址,虚拟主机名中不能有下划线_
 */
@FeignClient(name = "microservice-user")
public interface UserFeignClient {
    /**PathVariable()中必须加参数名，否则无法创建此接口的实例*/
    @GetMapping(value = "/user/{id}")
    User findUserById(@PathVariable("id") int id);

    @GetMapping(value = "/user/discountbyuserid/{id}")
    BigDecimal getDiscountByUserId(@PathVariable("id") int id);
}

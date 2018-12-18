package jason.luo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

/**
 * Spring Cloud 与 Docker 微服务架构实战
 * 用户微服务
 * Mybatis实例化默认的dao给controller注入
 * TODO: 添加注册功能，微服务之间调用的权限问题
 */
@SpringBootApplication
@MapperScan(basePackages = "jason.luo.dao",annotationClass = Repository.class)
public class UserApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserApp.class, args);
    }
}

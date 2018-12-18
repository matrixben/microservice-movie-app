package jason.luo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

/**
 * Spring Cloud 与 Docker 微服务架构实战
 * 电影微服务
 * 表结构参考: https://blog.csdn.net/johnli1989/article/details/79491519
 * TODO: 单元测试,页面保存用户信息,auth2权限管理
 */
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@MapperScan(basePackages = "jason.luo.dao",annotationClass = Repository.class)
public class MovieApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(MovieApp.class, args);
    }
}

package jason.luo;

import jason.luo.filters.pre.SimpleUserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * 网关zuul服务端
 *
 */
@SpringBootApplication
@EnableZuulProxy
public class GatewayApp {
    public static void main( String[] args ) {
        SpringApplication.run(GatewayApp.class, args);
    }
}

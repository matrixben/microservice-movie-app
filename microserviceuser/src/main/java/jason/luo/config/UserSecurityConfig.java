package jason.luo.config;

import jason.luo.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserLoginService userLoginService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码不能明文保存在数据库
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userLoginService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置按靠前优先原则，当前后配置冲突时，使用靠前的配置
        http.anonymous();
        /*
                .authorizeRequests()
                //限定指定路径的指定访问角色
                .antMatchers("/user/discountbyuserid/**").hasAuthority("管理员")
                .antMatchers("/user/**").hasAnyAuthority("管理员","用户")
                //对于没有指定的其他路径，允许所有访问
                .anyRequest().permitAll()
                .and()
                //使用默认登录界面
                .formLogin().loginPage("/login").defaultSuccessUrl("/hi")
                .and()
                //自定义登出界面，添加登出成功后跳转界面
                .logout().logoutUrl("/logout").logoutSuccessUrl("/hi")
                //启用默认http验证
                .and().httpBasic();
                */
    }
}

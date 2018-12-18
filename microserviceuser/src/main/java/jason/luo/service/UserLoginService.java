package jason.luo.service;

import jason.luo.dao.UserDao;
import jason.luo.domain.LoginRole;
import jason.luo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByName(s);
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //添加用户权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<LoginRole> roles = userDao.getRolesFromUser(user.getUserId());
        for (LoginRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            System.out.println(role.getRoleName());
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getUserPassword(), authorities);
    }
}

package jason.luo.dao;

import jason.luo.domain.LoginRole;
import jason.luo.domain.User;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserDao {
    User getUser(int id);

    User getUserByName(String name);

    List<User> getUserByClassName(String name);

    BigDecimal getDiscountByUserId(int id);

    List<LoginRole> getRolesFromUser(int id);
}

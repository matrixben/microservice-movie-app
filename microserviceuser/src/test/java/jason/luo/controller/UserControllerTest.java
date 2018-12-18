package jason.luo.controller;

import jason.luo.dao.UserDao;
import jason.luo.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDao userDao;
    
    private User user1;
    private User user2;
    private BigDecimal disCount;
    
    @Before
    public void setUp(){
        user1 = new User();
        user1.setUserId(1);
        user1.setUsername("jason");
        user1.setRealname("real jason");
        user1.setAge(2);
        user1.setUserEmail("email");
        user1.setClassId(3);
        user1.setUserPassword(null);

        user2 = new User();
        user2.setUserId(2);
        user2.setUsername("jason2");
        user2.setRealname("real jason2");
        user2.setAge(20);
        user2.setUserEmail("email2");
        user2.setClassId(3);
        user2.setUserPassword(null);

        disCount = new BigDecimal(0.99);
    }

    @Test
    @WithMockUser(username = "user1",password = "123456")
    public void findUserById() throws Exception {
        int arg = 1;
        Mockito.when(userDao.getUser(arg)).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/"+arg))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                .string("{\"userId\":1,\"username\":\"jason\",\"realname\":\"real jason\"," +
                        "\"age\":2,\"userEmail\":\"email\",\"classId\":3,\"userPassword\":null}"));
//                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("jason")));
    }

    @Test
    @WithMockUser(username = "user1",password = "123456")
    public void findUserByName() throws Exception {
        String arg = "user1";
        Mockito.when(userDao.getUserByName(arg)).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/name/"+arg))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userId\":1,\"username\":\"jason\",\"realname\":\"real jason\"," +
                                "\"age\":2,\"userEmail\":\"email\",\"classId\":3,\"userPassword\":null}"));
    }

    @Test
    @WithMockUser(username = "user1",password = "123456")
    public void findUserByClassName() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        String arg = "className";
        Mockito.when(userDao.getUserByClassName(arg)).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/class/"+arg))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                .string("[{\"userId\":1,\"username\":\"jason\",\"realname\":\"real jason\",\"age\":2,\"userEmail\":\"email\",\"classId\":3,\"userPassword\":null},{\"userId\":2,\"username\":\"jason2\",\"realname\":\"real jason2\",\"age\":20,\"userEmail\":\"email2\",\"classId\":3,\"userPassword\":null}]"));
    }

    @Test
    @WithMockUser(username = "user1",password = "123456")
    public void getDiscountByUserId() throws Exception {
        Mockito.when(userDao.getDiscountByUserId(user1.getUserId())).thenReturn(disCount);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/discountbyuserid/"+user1.getUserId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .string(disCount.toString()));

    }
}
package jason.luo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/logout")
    public String logout(){
        return "userlogout";
    }

    @RequestMapping("/login")
    public String userLogin(){
        return "login";
    }
}

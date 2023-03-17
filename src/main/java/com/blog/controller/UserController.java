package com.blog.controller;

import com.blog.model.UserModel;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping ("/check-login")
    public String checkLogin(UserModel userModel,Model model) {
        boolean isLogin = userService.checkLogin(userModel);
        if (!isLogin){
            session.setAttribute("message","success");
            return "redirect:/login-page";
        }
        session.setAttribute("userSession",userModel);
        return "redirect:/home";
    }

    @GetMapping("/login-page")
    public String login(Model model){
        model.addAttribute("user",new UserModel());
        model.addAttribute("message",session.getAttribute("message"));
        return "login";
    }

    @GetMapping("/dang-ky")
    public String signup(){
        return "register";
    }

    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("userSession");
        return "index";
    }
}

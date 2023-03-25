package com.blog.controller;

import com.blog.service.CategoryService;
import com.blog.service.PostService;
import com.blog.service.UserService;
import com.blog.model.CategoryModel;
import com.blog.model.PostModel;
import com.blog.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HttpSession session;

    /**
     * Kiểm tra đăng nhập
     *
     * @param userModel
     * @param model
     * @return String
     */
    @PostMapping ("/check-login")
    public String checkLogin(UserModel userModel,Model model) {
        UserModel user = userService.checkLogin(userModel);
        if (user==null){
            session.setAttribute("message","Tài khoản hoặc mật khẩu không đúng!");
            return "redirect:/login-page";
        }
        session.setAttribute("userSession",user);
        return "redirect:/home";
    }

    /**
     * Trang đăng nhập
     *
     * @param model
     * @return String
     */
    @GetMapping("/login-page")
    public String login(Model model){
        model.addAttribute("warning", session.getAttribute("message2"));
        model.addAttribute("user",new UserModel());
        model.addAttribute("message",session.getAttribute("message"));
        return "login";
    }


        /**
     * Trang đăng nhập
     *
     * @param model
     * @return String
     */
    @GetMapping("/profile")
    public String profile(Model model){
        UserModel user = (UserModel) session.getAttribute("userSession");
        if(user==null){
            return "redirect:/login-page";
        }
        model.addAttribute("userSession", user);
        List<CategoryModel> categoryModels = categoryService.findAllNav();
        List<PostModel> postPopular = postService.findByPopular();
        List<PostModel> postByUser = postService.findByUserId(user.getId());
        model.addAttribute("popular",postPopular);
        model.addAttribute("categories",categoryModels);
        model.addAttribute("postUser",postByUser);
        return "/web/profile";
    }


    /**
     * Trang đăng ký
     *
     * @return String
     */
    @GetMapping("/dang-ky")
    public String signup(){
        return "register";
    }

    /**
     * Đăng xuất
     *
     * @return String
     */
    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("userSession");
        return "redirect:/home";
    }
}

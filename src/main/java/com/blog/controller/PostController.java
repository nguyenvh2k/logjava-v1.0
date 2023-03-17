package com.blog.controller;

import com.blog.model.PostModel;
import com.blog.model.UserModel;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PostService postService;

    @GetMapping({"/home","/"})
    public String index(Model model){
        UserModel user = (UserModel) session.getAttribute("userSession");
        model.addAttribute("userSession",user);
        List<PostModel> posts = postService.findAll();
        model.addAttribute("posts",posts);
        return "index";
    }
    @GetMapping({"/post"})
    public String createNewPost(Model model){
        UserModel user = (UserModel) session.getAttribute("userSession");
        model.addAttribute("userSession",user);
        return "post";
    }
}

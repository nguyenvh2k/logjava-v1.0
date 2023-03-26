package com.blog.controller;

import com.blog.model.CategoryModel;
import com.blog.model.PostModel;
import com.blog.model.UserModel;
import com.blog.service.CategoryService;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class PostController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Hiển thị trang chủ và load
     * Danh sách bài viết, chủ đề, Bài viết nổi bật
     *
     * @param model
     * @return String
     */
    @GetMapping({"/home","/"})
    public String index(Model model){
        UserModel user = (UserModel) session.getAttribute("userSession");
        model.addAttribute("userSession",user);
        List<PostModel> posts = postService.findAll();
        model.addAttribute("posts",posts);
        List<CategoryModel> categoryModels = categoryService.findAllNav();
        List<PostModel> postPopular = postService.findByPopular();
        model.addAttribute("popular",postPopular);
        model.addAttribute("categories",categoryModels);
        return "/web/home";
    }

    /**
     * Trang thêm mới bài viết
     * Danh sách bài viết, chủ đề, Bài viết nổi bật
     *
     * @param model
     * @return String
     */
    @GetMapping({"/post"})
    public String createNewPost(Model model){
        UserModel user = (UserModel) session.getAttribute("userSession");
        model.addAttribute("userSession",user);
        if (user==null){
            session.setAttribute("message2", "Login to write blog");
            return "redirect:/login-page";
        }
        List<CategoryModel> categories =categoryService.findAll();
        model.addAttribute("categoryPost",categories);
        List<CategoryModel> categoryModels = categoryService.findAllNav();
        List<PostModel> postPopular = postService.findByPopular();
        model.addAttribute("popular",postPopular);
        model.addAttribute("categories",categoryModels);
        return "/web/create-post";
    }

    /**
     * Trang chi tiết bài viết
     *
     *
     * @param id
     * @param model
     * @return String
     */
    @GetMapping({"/new-post/{id}"})
    public String blog(@PathVariable("id")Long id, Model model){
        UserModel user = (UserModel) session.getAttribute("userSession");
        model.addAttribute("userSession",user);
        List<CategoryModel> categories =categoryService.findAll();
        model.addAttribute("categoryPost",categories);
        List<CategoryModel> categoryModels = categoryService.findAllNav();
        model.addAttribute("categories",categoryModels);
        List<PostModel> postPopular = postService.findByPopular();
        model.addAttribute("popular",postPopular);
        PostModel post = postService.findById(id);
        model.addAttribute("post",post);
        List<PostModel> commentList = postService.findComment(id);
        model.addAttribute("comments",commentList);
        return "/web/post";
    }

    /**
     * Trang cập nhật bài viết
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit-post/{id}")
    public String editBlog(@PathVariable("id")Long id,Model model){
        UserModel user = (UserModel) session.getAttribute("userSession");
        model.addAttribute("userSession",user);
        if (user==null){
            session.setAttribute("message2", "Login to write blog");
            return "redirect:/login-page";
        }
        PostModel post = postService.findById(id);
        model.addAttribute("post",post);
        List<CategoryModel> categories =categoryService.findAll();
        model.addAttribute("categoryPost",categories);
        List<CategoryModel> categoryModels = categoryService.findAllNav();
        model.addAttribute("categories",categoryModels);
        List<PostModel> postPopular = postService.findByPopular();
        model.addAttribute("popular",postPopular);
        return "/web/edit-post";
    }

    @GetMapping("/test")
    public String test(){
        return "index";
    }
}
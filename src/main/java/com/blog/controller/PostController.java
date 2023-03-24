package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class PostController {



    /**
     * Hiển thị trang chủ và load
     * Danh sách bài viết, chủ đề, Bài viết nổi bật
     *
     * @param model
     * @return String
     */
    @GetMapping({"/home","/"})
    public String index(Model model){

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
        return "/web/edit-post";
    }
}
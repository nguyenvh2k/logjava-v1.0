package com.blog.service;

import com.blog.model.PostModel;

import java.util.List;

public interface PostService {
    List<PostModel> findAll();
}

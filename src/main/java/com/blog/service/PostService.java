package com.blog.service;

import com.blog.model.PostModel;

import java.util.List;

public interface PostService {
    List<PostModel> findAll();
    List<PostModel> findByPopular();
    PostModel findById(Long id);
    Boolean insert(PostModel postModel);
    List<PostModel> findComment(Long id);
    void update(PostModel postModel);
}

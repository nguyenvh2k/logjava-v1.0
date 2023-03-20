package com.blog.repository;

import com.blog.model.PostModel;

import java.util.List;

public interface PostRepository {
    List<PostModel> findAll();
    PostModel findById(Long id);
    List<PostModel> findByPopular();
    Boolean insert(PostModel postModel);
}

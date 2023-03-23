package com.blog.repository;

import com.blog.model.PostModel;

import java.util.List;

public interface PostRepository {
    List<PostModel> findAll();
    PostModel findById(Long id);
    List<PostModel> findByPopular();
    Boolean insert(PostModel postModel);
    List<PostModel> findComment(Long id);
    void update(PostModel postModel);
    void delete(Long id);
}

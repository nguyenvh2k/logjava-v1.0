package com.blog.repository;

import com.blog.model.PostModel;

import java.util.List;

public interface PostRepository {
    List<PostModel> findAll();
}

package com.blog.service.impl;

import com.blog.model.PostModel;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostModel> findAll() {
        return postRepository.findAll();
    }

}

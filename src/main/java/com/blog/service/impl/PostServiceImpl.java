package com.blog.service.impl;

import com.blog.repository.CategoryRepository;
import com.blog.model.CategoryModel;
import com.blog.model.PostModel;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<PostModel> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<PostModel> findByPopular() {
        return postRepository.findByPopular();
    }

    @Override
    public PostModel findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Boolean insert(PostModel postModel) {
        CategoryModel category = categoryRepository.findByName(postModel.getCategoryCode());
        postModel.setCategoryId(category.getId());
        postModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        postModel.setCreatedBy("ADMIN");
        return postRepository.insert(postModel);
    }

    @Override
    public List<PostModel> findComment(Long id) {
        return postRepository.findComment(id);
    }

    @Override
    public void update(PostModel postModel) {
        CategoryModel category = categoryRepository.findByName(postModel.getCategoryCode());
        postModel.setCategoryId(category.getId());
        postRepository.update(postModel);
    }

}

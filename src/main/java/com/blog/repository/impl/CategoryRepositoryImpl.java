package com.blog.repository.impl;

import com.blog.model.CategoryModel;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public List<CategoryModel> findAll() {
        return null;
    }
}

package com.blog.service.impl;

import com.blog.model.CategoryModel;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryModel> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryModel> findAllNav() {
        return categoryRepository.findAllNav();
    }
}

package com.blog.repository;

import com.blog.model.CategoryModel;

import java.util.List;

public interface CategoryRepository {
    List<CategoryModel> findAll();
}

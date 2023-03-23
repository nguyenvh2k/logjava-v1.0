package com.blog.repository.impl;

import com.blog.utils.MySQLUtil;
import com.blog.model.CategoryModel;
import com.blog.repository.CategoryRepository;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    /**
     * Lấy danh sách Category từ database
     *
     * @return List<CategoryModel>
     */
    @Override
    public List<CategoryModel> findAll() {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from category";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setCode(resultSet.getString("code"));
                categoryModel.setName(resultSet.getString("name"));
                categoryModelList.add(categoryModel);
            }
        }catch (SQLException e){
            System.out.println("Co Execption o category repo:");
            System.out.println(e.getMessage());
        }
        return categoryModelList;
    }

    /**
     * Lấy danh sách số lượng các Category từ database
     *
     * @return List<CategoryModel>
     */
    @Override
    public List<CategoryModel> findAllNav() {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select count(*),c.name,c.code  from category c join posts p on p.category_id = c.id group by c.name ,c.code ;";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setCode(resultSet.getString("code"));
                categoryModel.setName(resultSet.getString("name"));
                categoryModel.setCount(resultSet.getInt("count"));
                categoryModelList.add(categoryModel);
            }
        }catch (SQLException e){
            System.out.println("Co Execption o category repo:");
            System.out.println(e.getMessage());
        }
        return categoryModelList;
    }

    /**
     * Tìm category theo tên
     *
     * @return CategoryModel
     */
    @Override
    public CategoryModel findByName(String name) {
        CategoryModel category = new CategoryModel();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from category where code = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                category.setId(resultSet.getLong("id"));
                category.setCode(resultSet.getString("code"));
                category.setName(resultSet.getString("name"));
            }
        }catch (SQLException e){
            System.out.println("Co Execption o category repo:");
            System.out.println(e.getMessage());
        }
        return category;
    }
}

package com.blog.repository.impl;

import com.blog.model.PostModel;
import com.blog.repository.PostRepository;
import com.blog.utils.MySQLUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    @Override
    public List<PostModel> findAll() {
        List<PostModel> posts = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            String sql = "select * from posts";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                PostModel postModel = new PostModel();
                postModel.setId(resultSet.getLong("id"));
                postModel.setTitle(resultSet.getString("title"));
                postModel.setThumbnail(resultSet.getString("thumbnail"));
                postModel.setImage(resultSet.getString("image"));
                postModel.setContent(resultSet.getString("content"));
                postModel.setCreatedDate(resultSet.getTimestamp("created_date"));
                posts.add(postModel);
            }
        }catch (SQLException e){
            System.out.println("Xay ra exception!");
            System.out.println(e.getMessage());
        }
        return posts;
    }

    @Override
    public PostModel findById(Long id) {
        PostModel postModel = new PostModel();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            String sql = "select * from posts s join category c on c.id = s.category_id where s.id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                postModel.setId(resultSet.getLong("id"));
                postModel.setTitle(resultSet.getString("title"));
                postModel.setThumbnail(resultSet.getString("thumbnail"));
                postModel.setImage(resultSet.getString("image"));
                postModel.setContent(resultSet.getString("content"));
                postModel.setCategoryName(resultSet.getString("name"));
                postModel.setCreatedDate(resultSet.getTimestamp("created_date"));
            }
        }catch (SQLException e){
            System.out.println("Xay ra exception!");
            System.out.println(e.getMessage());
        }
        return postModel;
    }

    @Override
    public List<PostModel> findByPopular() {
        List<PostModel> posts = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            String sql = "select * from posts limit 3;";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                PostModel postModel = new PostModel();
                postModel.setId(resultSet.getLong("id"));
                postModel.setTitle(resultSet.getString("title"));
                postModel.setThumbnail(resultSet.getString("thumbnail"));
                postModel.setImage(resultSet.getString("image"));
                postModel.setContent(resultSet.getString("content"));
                postModel.setCreatedDate(resultSet.getTimestamp("created_date"));
                posts.add(postModel);
            }
        }catch (SQLException e){
            System.out.println("Xay ra exception!");
            System.out.println(e.getMessage());
        }
        return posts;
    }

    @Override
    public Boolean insert(PostModel postModel) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MySQLUtil.getConnection();
            String sql = "insert into posts(title,image,short_description,content,category_id,user_id,created_date,created_by) values(?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setString(1, postModel.getTitle());
            statement.setString(2, postModel.getImage());
            statement.setString(3, postModel.getShortDescription());
            statement.setString(4, postModel.getContent());
            statement.setLong(5, postModel.getCategoryId());
            statement.setLong(6, postModel.getUserId());
            statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            statement.setString(8, postModel.getCreatedBy());
            statement.executeUpdate();
            connection.commit();
            System.out.println("Insert post success !");
            return true;
        }catch(SQLException e){
            System.out.println("Insert post failed !");
            System.out.println("Xay ra exception o insert post repo !");
            System.out.println("Exception: "+e.getMessage());
            try {
                connection.rollback();
                System.out.println("Rollback thanh cong !");
            } catch (SQLException e1) {
                System.out.println("Roll back that bai !");
            }
        }finally{
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    
}

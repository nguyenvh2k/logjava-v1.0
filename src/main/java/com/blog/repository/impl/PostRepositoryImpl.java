package com.blog.repository.impl;

import com.blog.model.PostModel;
import com.blog.repository.PostRepository;
import com.blog.utils.MySQLUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                postModel.setCreatedDate(resultSet.getTimestamp("createddate"));
                posts.add(postModel);
            }
        }catch (SQLException e){
            System.out.println("Xay ra exception!");
            System.out.println(e.getMessage());
        }
        return posts;
    }
}

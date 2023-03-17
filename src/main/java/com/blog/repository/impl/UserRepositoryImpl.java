package com.blog.repository.impl;

import com.blog.model.UserModel;
import com.blog.repository.UserRepository;
import com.blog.utils.MySQLUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public UserModel findByUsername(UserModel userModel) {
        UserModel user = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            String sql = "select * from users where username=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userModel.getUsername());
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new UserModel();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
            }
            return user;
        }catch (SQLException e){
            System.out.println("Xay ra Exception o UserRepository:");
            System.out.println(e.getMessage());
        }finally {
            try {
                connection.close();
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Boolean insert(UserModel userModel) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into users(username,password,email,fullname,role) values(?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userModel.getUsername());
            statement.setString(2,userModel.getPassword());
            statement.setString(3,userModel.getEmail());
            statement.setString(4,userModel.getFullname());
            statement.setInt(5,userModel.getRole());
            statement.executeUpdate();
            connection.commit();
            System.out.println("Insert success");
            return true;
        }catch (SQLException e){
            System.out.println("Xay ra Exception o UserRepository:");
            System.out.println(e.getMessage());
            try {
                connection.rollback();
                System.out.println("Rollback success!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

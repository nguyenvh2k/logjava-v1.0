package com.blog.repository.impl;

import com.blog.repository.UserRepository;
import com.blog.utils.MySQLUtil;
import com.blog.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Tìm User theo tên
     *
     * @param userModel
     * @return UserModel
     */
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
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setFullname(resultSet.getString("fullname"));
                user.setEmail(resultSet.getString("email"));
                user.setImage(resultSet.getString("avatar"));
            }
            logger.info("Find user by name success");
            return user;
        }catch (SQLException e){
            logger.error("Xay ra Exception o UserRepository:",e.getMessage());
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

    /**
     * Thêm mới user
     *
     * @param userModel
     * @return Boolean
     */
    @Override
    public Boolean insert(UserModel userModel) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into users(username,password,email,fullname,roleid,status,avatar) values(?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userModel.getUsername());
            statement.setString(2,userModel.getPassword());
            statement.setString(3,userModel.getEmail());
            statement.setString(4,userModel.getFullname());
            statement.setInt(5,userModel.getRole());
            statement.setInt(6, 1);
            statement.setString(7,userModel.getImage());
            statement.executeUpdate();
            connection.commit();
            logger.info("Insert success !");
            return true;
        }catch (SQLException e){
            logger.error("Insert failed !");
            logger.error("Exception: {}",e.getMessage());
            try {
                connection.rollback();
                logger.info("Rollback success!");
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

    @Override
    public void updateAvatar(UserModel userModel) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update users set avatar = ? where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userModel.getImage());
            statement.setLong(2,userModel.getId());
            statement.executeUpdate();
            connection.commit();
            logger.info("Update avatar success !");
        }catch (SQLException e){
            logger.error("Update avatar failed !");
            logger.error("Exception :{}",e.getMessage());
            try {
                connection.rollback();
                logger.warn("Rollback success !");
            } catch (SQLException ex) {
                logger.error("Rollback failed !");
            }
        }
    }
}

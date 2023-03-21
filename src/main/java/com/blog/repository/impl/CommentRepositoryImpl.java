package com.blog.repository.impl;

import com.blog.model.Comment;
import com.blog.repository.CommentRepository;
import com.blog.utils.MySQLUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @Override
    public boolean insert(Comment comment) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert  into comment(content,user_id,new_id,created_date) values (?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,comment.getContent());
            statement.setLong(2,comment.getUserId());
            statement.setLong(3,comment.getPostId());
            statement.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
            connection.commit();
            System.out.println("Insert comment success !");
            return true;
        }catch (SQLException e){
            System.out.println("Xay ra exception o Comment:");
            System.out.println(e.getMessage());
            try {
                connection.rollback();
                System.out.println("Rollback success !");
            } catch (SQLException ex) {
                System.out.println("Rollback Failed !");
                System.out.println(ex.getMessage());
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

package com.blog.repository.impl;

import com.blog.utils.MySQLUtil;
import com.blog.model.Comment;
import com.blog.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    /**
     * Thêm mới bình luận (Comment)
     *
     * @param comment
     * @return Long
     */
    @Override
    public Long insert(Comment comment) {
        Long id = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet =null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert  into comment(content,user_id,post_id,created_date) values (?,?,?,?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,comment.getContent());
            statement.setLong(2,comment.getUserId());
            statement.setLong(3,comment.getPostId());
            statement.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            connection.commit();
            System.out.println("Insert comment success !");
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
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
        return id;
    }

    /**
     * Tìm bình luận theo id
     *
     * @param id
     * @return Comment
     */
    @Override
    public Comment findById(Long id) {
        Comment comment = new Comment();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            String sql = "select * from comment where id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                comment.setUserId(resultSet.getLong("user_id"));
                comment.setCreatedDate(resultSet.getTimestamp("created_date"));
                comment.setPostId(resultSet.getLong("new_id"));
                comment.setContent(resultSet.getString("content"));
                comment.setId(resultSet.getLong("id"));

            }
            System.out.println("Lay danh sach comment thanh cong !");
        }catch (SQLException e){
            System.out.println("Xay ra exception!");
            System.out.println(e.getMessage());
        }
        return comment;
    }

    /**
     * Xoá bình luận theo id
     *
     * @param id
     * @return Comment
     */
    @Override
    public void delete(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "delete from comment where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            statement.executeUpdate();
            connection.commit();
            System.out.println("Delete comment success !");
        } catch (SQLException e) {
            System.out.println("Xay ra Exception khi xoa comment !");
            System.out.println(e.getMessage());
            try {
                connection.rollback();
                System.out.println("Rollback success !");
            } catch (SQLException ex) {
                System.out.println("Rollback failed");
                ex.printStackTrace();
            }
        }
    }

}

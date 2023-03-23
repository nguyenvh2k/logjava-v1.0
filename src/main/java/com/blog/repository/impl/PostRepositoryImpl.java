package com.blog.repository.impl;

import com.blog.utils.MySQLUtil;
import com.blog.model.Comment;
import com.blog.model.PostModel;
import com.blog.model.UserModel;
import com.blog.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");


    /**
     * Lấy danh sách tất cả bài viết
     *
     * @return List<PostModel>
     */
    @Override
    public List<PostModel> findAll() {
        List<PostModel> posts = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            String sql = "select * from posts p join users u on u.id = p.user_id join category c2 on c2.id = p.category_id";
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
                postModel.setCategoryName(resultSet.getString("name"));
                UserModel userModel = new UserModel();
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setUsername(resultSet.getString("username"));
                userModel.setImage(resultSet.getString("avatar"));
                postModel.setUserModel(userModel);
                postModel.setDate(dateFormat.format(new Date(postModel.getCreatedDate().getTime())));
                System.out.println(postModel.getDate());
                posts.add(postModel);
            }
            logger.info("Get the list of successful posts!");
        }catch (SQLException e){
            logger.error("Get list of failed posts !");
            logger.error("Exception : {}",e.getMessage());
        }
        return posts;
    }

    /**
     * Tìm bài viết theo id
     *
     * @return PostModel
     */
    @Override
    public PostModel findById(Long id) {
        PostModel postModel = new PostModel();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLUtil.getConnection();
            String sql = "select\n" +
                    "\t*\n" +
                    "from\n" +
                    "\tposts p\n" +
                    "join users u on\n" +
                    "\tu.id = p.user_id\n" +
                    "join category c2 on\n" +
                    "\tc2.id = p.category_id where p.id = ?;";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                postModel.setId(resultSet.getLong("id"));
                postModel.setTitle(resultSet.getString("title"));
                postModel.setThumbnail(resultSet.getString("thumbnail"));
                postModel.setShortDescription(resultSet.getString("short_description"));
                postModel.setImage(resultSet.getString("image"));
                postModel.setContent(resultSet.getString("content"));
                postModel.setCategoryName(resultSet.getString("name"));
                postModel.setCreatedDate(resultSet.getTimestamp("created_date"));
                postModel.setUserId(resultSet.getLong("user_id"));
                UserModel userModel = new UserModel();
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setUsername(resultSet.getString("username"));
                userModel.setImage(resultSet.getString("avatar"));
                postModel.setDate(dateFormat.format(new Date(postModel.getCreatedDate().getTime())));
                postModel.setUserModel(userModel);
            }
            logger.info("Get list of posts by id success !");
        }catch (SQLException e){
            logger.error("Get list of failed posts !");
            logger.error("Exception : {}",e.getMessage());
        }
        return postModel;
    }

    /**
     * Lấy danh sách bài viết nổi bật
     *
     * @return List<PostModel>
     */
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
                postModel.setDate(dateFormat.format(new Date(postModel.getCreatedDate().getTime())));
                posts.add(postModel);
            }
            logger.info("Get the list of highly viewed posts successfully !");
        }catch (SQLException e){
            logger.error("Get list of posts by id failed !");
            logger.error("Exception : {}",e.getMessage());
        }
        return posts;
    }

    /**
     * Thêm mới bài viết
     *
     * @param postModel
     * @return Boolean
     */
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
            logger.info("Insert post success !");
            return true;
        }catch(SQLException e){
            logger.error("Insert post failed !");
            logger.error("Exception : {}",e.getMessage());
            try {
                connection.rollback();
                logger.warn("Rollback Success !");
            } catch (SQLException e1) {
                logger.error("Rollback Failed !");
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

    @Override
    public List<PostModel> findComment(Long id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<PostModel> commentList = new ArrayList<>();
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select\n" +
                    "\tc.content ,u.username ,u.fullname \n,c.created_date ,u.avatar " +
                    "from\n" +
                    "\tposts p\n" +
                    "left join comment c on\n" +
                    "\tc.post_id = p.id\n" +
                    "left join users u on\n" +
                    "\tu.id = c.user_id\n" +
                    "join category c2 on\n" +
                    "\tc2.id = p.category_id where p.id = ?;";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                PostModel postModel = new PostModel();
                Comment comment = new Comment();
                comment.setContent(resultSet.getString("content"));
                comment.setCreatedDate(resultSet.getTimestamp("created_date"));
                UserModel userModel = new UserModel();
                userModel.setUsername(resultSet.getString("username"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setImage(resultSet.getString("avatar"));
                postModel.setComment(comment);
                postModel.setUserModel(userModel);
                commentList.add(postModel);
                logger.info("Find comment success !");
            }
        }catch (SQLException e){
            logger.error("Find comment failed exception : {}",e.getMessage());
        }finally {
            try {
                connection.close();
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return commentList;
    }

    /**
     * Cập nhật/Chỉnh sửa bài viết
     *
     * @param postModel
     */
    @Override
    public void update(PostModel postModel) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection =MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update posts set title = ?, short_description = ?,content=?,category_id=?,image=?,created_date=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,postModel.getTitle());
            statement.setString(2,postModel.getShortDescription());
            statement.setString(3,postModel.getContent());
            statement.setLong(4,postModel.getCategoryId());
            statement.setString(5,postModel.getImage());
            statement.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
            statement.setLong(7,postModel.getId());
            statement.executeUpdate();
            connection.commit();
            logger.info("Update post success !");
        }catch (SQLException e){
            logger.error("Update post failed exception: {}",e.getMessage());
            try {
                connection.rollback();
                logger.warn("Rollback Success !");
            } catch (SQLException ex) {
                logger.error("Rollback Failed !");
                System.out.println(e.getMessage());
            }
        }finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                logger.error("Close connection failed !");
            }
        }
    }

    /**
     * Xoá bài viết và xoá comment của bài viết đó
     *
     */
    public void delete(Long id){
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            connection = MySQLUtil.getConnection();
            connection.setAutoCommit(false);
            String sqlComment = "delete from comment where post_id=?";
            statement1 = connection.prepareStatement(sqlComment);
            statement1.setLong(1,id);
            statement1.executeUpdate();
            String sqlPost = "delete from posts where id=?";
            statement2 = connection.prepareStatement(sqlPost);
            statement2.setLong(1,id);
            statement2.executeUpdate();
            connection.commit();
            logger.info("Delete posts success !");
        }catch (SQLException e){
            logger.error("Delete post failed Exception: {}",e.getMessage());
            try {
                connection.rollback();
                logger.warn("Rollback success !");
            } catch (SQLException ex) {
                logger.warn("Rollback failed !");
            }
        }finally {
            try {
                connection.close();
                statement1.close();
                statement2.close();
            } catch (SQLException e) {
                logger.error("Close connection failed !");
            }
        }

    }
}

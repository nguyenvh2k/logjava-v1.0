package com.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Kết nôi database
     * @return Connection
     */
    public static Connection getConnection(){
        try {
//            logger.info("Connecting....");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/blog";
            String user ="root";
            String password ="123456";
            Connection connection = DriverManager.getConnection(url,user,password);
//            logger.info("Connect success !");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed :"+e.getMessage());
        }
        return null;
    }
}

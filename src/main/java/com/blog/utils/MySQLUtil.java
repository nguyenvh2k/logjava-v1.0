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

    public Connection createConnection(){
        try {
            logger.info("Connecting....");
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://db.uoddykgmypogipcizabv.supabase.co:5432/postgres";
            String user ="postgres";
            String password ="iLOVEYOU124@#$%^&*()";
            Connection connection = DriverManager.getConnection(url,user,password);
            logger.info("Connect success !");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Connection Failed :{}"+e.getMessage());
        }
        return null;
    }

    public static Connection getConnection(){
        return new MySQLUtil().createConnection();
    }
}

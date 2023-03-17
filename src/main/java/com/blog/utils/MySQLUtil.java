package com.blog.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLUtil {

    public static Connection getConnection(){
        try {
            System.out.println("Connecting...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/blog";
            String user ="root";
            String password ="123456";
            Connection connection = DriverManager.getConnection(url,user,password);
            System.out.println("Connect success !");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connect failed! Exception:");
            System.out.println(e.getMessage());
        }
        return null;
    }
}

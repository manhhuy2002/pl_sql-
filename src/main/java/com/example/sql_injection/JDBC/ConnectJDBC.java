package com.example.sql_injection.JDBC;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class ConnectJDBC {
    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver"); // Thêm dòng này để tải lớp Driver
//            String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
            String url = "jdbc:postgresql://192.168.138.131:5432/postgres";
            String username = "postgres";
            String password = "manhhuy2002";
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return connection;
    }
}
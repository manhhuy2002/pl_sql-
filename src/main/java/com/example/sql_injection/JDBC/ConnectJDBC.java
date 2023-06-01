package com.example.sql_injection.JDBC;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectJDBC {
    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Thêm dòng này để tải lớp Driver
//            String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
//            String url = "jdbc:postgresql://192.168.220.128:5432/postgres";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=mssql";
            String username = "sa";
            String password = "manhhuy2002";
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
        }
        return connection;
    }
}

package com.example.sql_injection.JDBC;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class ConnectJDBC {
    public Connection getConnection(){
        Connection connection = null;
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://127.0.0.1:3306/employees?createDatabaseIfNotExist=true";
            String username = "root";
            String password = "huytran1!!";
            connection = DriverManager.getConnection(url,username,password);

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return connection;
    }
}


package com.example.sql_injection.JDBC;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class ConnectJDBC {
    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("oracle.jdbc.OracleDriver"); // Sửa thành Oracle JDBC Driver
            String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Thêm dấu "//" trước localhost
            String username = "sys as sysdba"; // Sửa thành "sys as sysdba" để đăng nhập với quyền cao nhất
            String password = "123456"; // Thay đổi thông tin kết nối
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return connection;
    }
}

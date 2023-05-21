package com.example.sql_injection;

import com.example.sql_injection.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqlInjectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlInjectionApplication.class, args);
    }
    public Employee setEmployee(){
        Employee e = new Employee();
        return e;
    }
}

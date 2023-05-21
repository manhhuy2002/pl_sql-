package com.example.sql_injection.Exception;

import com.example.sql_injection.DTO.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;


@ControllerAdvice
@Slf4j
public class HandleException {

    @ExceptionHandler(SQLException.class)
    public String sqlException(SQLException sqlException, Model model){
        if (sqlException.getMessage().contains("SQL syntax")) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu sai rồi!");
        } else {
            model.addAttribute("error", sqlException.getMessage());
        }
        model.addAttribute("logindto",new LoginDTO());
        return "login";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model){
        model.addAttribute("error", exception.getMessage());
        model.addAttribute("logindto",new LoginDTO());
        return "login";
    }
}


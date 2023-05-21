package com.example.sql_injection.Exception;

import com.example.owasp10_springboot.DTO.LoginDTO;
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
        model.addAttribute("error",sqlException.getMessage());
        model.addAttribute("model",new LoginDTO());
        return "injection/sql/lab1/index";
    }
    @ExceptionHandler(Exception.class)
    public String exception(Exception exception){
        log.info(exception.toString());
        return "";
    }
}
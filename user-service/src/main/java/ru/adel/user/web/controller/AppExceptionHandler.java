package ru.adel.user.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.adel.user.domain.exception.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/errorPage";
    }

}

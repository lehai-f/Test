package it1.mock.hailh17.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
    public ModelAndView handle500Error(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error500"); // Thay "error" bằng tên trang lỗi của bạn
        modelAndView.addObject("errorMessage", "An internal server error occurred.");
        return modelAndView;
    }
}

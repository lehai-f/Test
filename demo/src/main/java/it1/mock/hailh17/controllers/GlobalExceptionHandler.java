package it1.mock.hailh17.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for exeption
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handle500Error(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error500"); // Thay "error" bằng tên trang lỗi của bạn
        modelAndView.addObject("errorMessage", "An internal server error occurred.");
        return modelAndView;
    }
}

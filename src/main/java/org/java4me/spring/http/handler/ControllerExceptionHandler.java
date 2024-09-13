package org.java4me.spring.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "org.java4Me.spring.http.controller")
public class ControllerExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }
}

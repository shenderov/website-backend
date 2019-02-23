package me.shenderov.website.handlers;

import me.shenderov.website.entities.ExceptionWrapper;
import me.shenderov.website.exceptions.MissingParameterException;
import me.shenderov.website.exceptions.RecaptchaValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({MissingParameterException.class})
    public ResponseEntity badRequest(HttpServletRequest req, MissingParameterException exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWrapper(HttpStatus.BAD_REQUEST, exception, req.getRequestURI()));
    }

    @ExceptionHandler({RecaptchaValidationException.class})
    public ResponseEntity badRequest(HttpServletRequest req, RecaptchaValidationException exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWrapper(HttpStatus.BAD_REQUEST, exception, req.getRequestURI()));
    }
}

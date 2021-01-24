package com.ivansop.weather.api.exception;

import com.ivansop.weather.api.dto.ExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> illegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}

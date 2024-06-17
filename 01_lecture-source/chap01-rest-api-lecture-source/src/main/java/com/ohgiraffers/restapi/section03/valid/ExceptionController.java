package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException e){
        String code= "ERROR_CODE_0001";
        String description = " 회원 정보 조회에 실패함";
        String detail = e.getMessage();

        return new ResponseEntity<>(new ErrorResponse(code,description,detail), HttpStatus.NOT_FOUND);
    }
}

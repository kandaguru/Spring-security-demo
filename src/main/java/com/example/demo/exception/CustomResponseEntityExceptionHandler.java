//package com.example.demo.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.nio.file.AccessDeniedException;
//
//@ControllerAdvice
//@RestController
//public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler
//    public  ResponseEntity<?> AccessDeniedException(AccessDeniedException ex, WebRequest req){
//        CustomExceptionMessage mess=new CustomExceptionMessage(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
//    }
//}

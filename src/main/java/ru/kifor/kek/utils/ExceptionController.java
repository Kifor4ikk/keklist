package ru.kifor.kek.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {


  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> catchNotFound(NotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotImplementedException.class)
  public ResponseEntity<String> catchNotImplemented(NotImplementedException ex) {
    return new ResponseEntity<>("Йоу йоу йоу, метод not implemented", HttpStatus.NOT_IMPLEMENTED);
  }

//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> catchGeneral(Exception ex) {
//    return new ResponseEntity<>("Alies kaput" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//  }

}

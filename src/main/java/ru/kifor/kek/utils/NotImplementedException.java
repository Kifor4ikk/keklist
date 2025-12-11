package ru.kifor.kek.utils;

import lombok.AllArgsConstructor;


public class NotImplementedException extends RuntimeException {

  public NotImplementedException(String message){
    super(message);
  }

}

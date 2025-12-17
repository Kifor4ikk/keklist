package ru.kifor.kek.utils;

public class AlreadyInGuildException extends RuntimeException {
  public AlreadyInGuildException(String message){
    super(message);
  }
}

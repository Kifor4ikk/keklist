package ru.kifor.kek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.kifor.kek.event.model.EventCreateModel;

@ComponentScan
@SpringBootApplication
public class Main {

  public static void main(String[] args){
    SpringApplication.run(Main.class, args);
  }
}

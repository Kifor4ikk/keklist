package ru.kifor.kek.base.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BasePageble <T> {

  private int page;
  private int limit;
  private int total;
  private List<T> data;


}

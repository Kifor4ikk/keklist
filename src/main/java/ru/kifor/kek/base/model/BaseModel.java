package ru.kifor.kek.base.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseModel {

  private Long id;
  private LocalDateTime createdAt;
  private LocalDateTime updateDate;

}

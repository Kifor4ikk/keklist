package ru.kifor.kek.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseUpdateModel {

  @Builder.Default
  private LocalDateTime updateDate = LocalDateTime.now();
}

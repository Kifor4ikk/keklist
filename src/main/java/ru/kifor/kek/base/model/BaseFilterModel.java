package ru.kifor.kek.base.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BaseFilterModel {

  @Builder.Default
  private int page = 0;

  @Builder.Default
  private int limit = 10;

  private Optional<LocalDate> dateMin;
  private Optional<LocalDate> dateMax;

}

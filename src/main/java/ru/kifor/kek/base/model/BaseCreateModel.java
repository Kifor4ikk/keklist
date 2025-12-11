package ru.kifor.kek.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseCreateModel {

  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();
}

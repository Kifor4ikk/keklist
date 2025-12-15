package ru.kifor.kek.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseCreateModel {

  @Builder.Default
  private Optional<LocalDateTime> createdAt = Optional.ofNullable(LocalDateTime.now());
}

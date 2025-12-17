package ru.kifor.kek.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseFilterModel;

import java.util.Optional;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventFilterModel extends BaseFilterModel {

  private Optional<String> name;

  private Optional<Integer> minGold;
  private Optional<Integer> maxGold;

  private Optional<Long> guildId;
}

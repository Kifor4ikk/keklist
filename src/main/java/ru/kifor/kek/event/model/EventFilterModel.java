package ru.kifor.kek.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseFilterModel;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventFilterModel extends BaseFilterModel {

  private String name;

  private int minGold;
  private int maxGold;

  private Long guildId;
}

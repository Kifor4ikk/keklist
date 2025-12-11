package ru.kifor.kek.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseUpdateModel;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventUpdateModel extends BaseUpdateModel {

  private String name;
  private int gold;
}

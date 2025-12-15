package ru.kifor.kek.person.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseCreateModel;
import ru.kifor.kek.enums.Spec;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonCreateModel extends BaseCreateModel {

  private String name;
  private int gearScore;
  private Spec spec;
  private Long guildId;
}

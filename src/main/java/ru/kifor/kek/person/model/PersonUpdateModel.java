package ru.kifor.kek.person.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseUpdateModel;
import ru.kifor.kek.enums.Spec;

import java.util.Optional;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonUpdateModel extends BaseUpdateModel {
  private Optional<String> name;
  private Optional<Integer> gearScore;
  private Optional<Spec> spec;
  private Optional<Long> guildId;
}

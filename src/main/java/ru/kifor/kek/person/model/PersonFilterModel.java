package ru.kifor.kek.person.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseFilterModel;
import ru.kifor.kek.enums.Spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonFilterModel extends BaseFilterModel {

  private Optional<String> name;
  private Optional<Integer> gearMin;
  private Optional<Integer> gearMax;
  @Builder.Default
  private List<Spec> spec = new ArrayList<>();
  private Optional<Long> guildId;

}

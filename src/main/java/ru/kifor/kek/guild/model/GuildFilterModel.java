package ru.kifor.kek.guild.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseFilterModel;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuildFilterModel extends BaseFilterModel {

  @Builder.Default
  private String name = null;
}

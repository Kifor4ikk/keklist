package ru.kifor.kek.guild.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseFilterModel;

import java.util.Optional;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuildFilterModel extends BaseFilterModel {

  private Optional<String> name;
}

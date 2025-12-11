package ru.kifor.kek.guild.model;

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
public class GuildUpdateModel extends BaseUpdateModel {
  private String name;
}

package ru.kifor.kek.guild.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseCreateModel;

@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GuildCreateModel extends BaseCreateModel {

  private String name;
  private Long ownerId;
}

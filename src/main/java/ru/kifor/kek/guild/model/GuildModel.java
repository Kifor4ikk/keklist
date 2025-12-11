package ru.kifor.kek.guild.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.person.model.PersonModel;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class GuildModel extends BaseModel {

  private String name;
  private List<PersonModel> members;
}

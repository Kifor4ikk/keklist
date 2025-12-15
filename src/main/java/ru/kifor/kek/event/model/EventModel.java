package ru.kifor.kek.event.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.person.model.PersonModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@Setter
public class EventModel extends BaseModel {
  private String name;
  private int gold;
  private GuildModel guild;
  private List<PersonModel> persons;

}

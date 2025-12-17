package ru.kifor.kek.invite.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.person.model.PersonModel;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InviteModel extends BaseModel {

  private PersonModel person;
  private GuildModel guild;
  private boolean isProcessed;
  private boolean result;
}

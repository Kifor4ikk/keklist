package ru.kifor.kek.invite.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseCreateModel;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InviteCreateModel extends BaseCreateModel {
  private Long personId;
  private Long guildId;
}

package ru.kifor.kek.invite.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseFilterModel;

import java.util.Optional;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InviteFilterModel extends BaseFilterModel {
  private Optional<Long> personId;
  private Optional<Long> guildId;
  private Optional<Boolean> isProcessed;
  private Optional<Boolean> result;
}

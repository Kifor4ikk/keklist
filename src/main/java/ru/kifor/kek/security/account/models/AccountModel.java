package ru.kifor.kek.security.account.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.enums.Roles;
import ru.kifor.kek.person.model.PersonModel;

@Getter
@Setter
@SuperBuilder
public class AccountModel extends BaseModel {
  private String login;
  private Roles role;
  private PersonModel personModel;
}

package ru.kifor.kek.security.account.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.enums.Roles;

@Getter
@Setter
@SuperBuilder
public class AccountModelCreate extends BaseModel {
  private String login;
  private String password;
  private Roles role;

}

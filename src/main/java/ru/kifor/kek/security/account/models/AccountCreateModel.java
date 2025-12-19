package ru.kifor.kek.security.account.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kifor.kek.base.model.BaseCreateModel;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AccountCreateModel extends BaseCreateModel {
  private String login;
  private String password;
}

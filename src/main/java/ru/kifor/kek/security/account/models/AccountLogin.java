package ru.kifor.kek.security.account.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kifor.kek.enums.Roles;

@Getter
@RequiredArgsConstructor
public class AccountLogin {
  String login;
  String password;
  Roles role;
}

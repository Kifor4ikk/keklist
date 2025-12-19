package ru.kifor.kek.security.account.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.enums.Roles;

@Getter
@SuperBuilder
@RequiredArgsConstructor
public class AccountCredentials {
  String login;
  String password;
  Roles role;
}

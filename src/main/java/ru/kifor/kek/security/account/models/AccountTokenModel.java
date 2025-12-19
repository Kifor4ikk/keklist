package ru.kifor.kek.security.account.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.kifor.kek.enums.Roles;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AccountTokenModel {

  private String login;
  private String password;
  private Roles role;
  private String token;
  private LocalDateTime expiredAt;
  private Long personId;
}

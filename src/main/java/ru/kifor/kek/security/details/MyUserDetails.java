package ru.kifor.kek.security.details;

import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kifor.kek.security.account.models.AccountCredentials;
import ru.kifor.kek.security.account.models.AccountLogin;

import java.util.Collection;
import java.util.List;

@Builder
public class MyUserDetails implements UserDetails {

  private AccountCredentials account;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(account.getRole().name()));
  }

  @Override
  public @Nullable String getPassword() {
    return account.getPassword();
  }

  @Override
  public String getUsername() {
    return account.getLogin();
  }
}

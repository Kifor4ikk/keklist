package ru.kifor.kek.security.auth;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.kifor.kek.security.account.service.AccountService;

import java.security.Principal;

@Component
public class AuthProvider implements AuthenticationProvider {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AccountService accountService;

  @Override
  public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Principal pr = (Principal) authentication.getCredentials();;
    return authentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return false;
  }
}

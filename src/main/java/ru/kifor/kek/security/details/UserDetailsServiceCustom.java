package ru.kifor.kek.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kifor.kek.security.account.service.AccountService;

@Component
public class UserDetailsServiceCustom implements UserDetailsService {

  @Autowired
  private AccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new MyUserDetails(accountService.getCredentials(username));
  }
}

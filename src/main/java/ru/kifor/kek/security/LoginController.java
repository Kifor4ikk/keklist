package ru.kifor.kek.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kifor.kek.security.account.models.AccountCreateModel;
import ru.kifor.kek.security.account.models.AccountLogin;
import ru.kifor.kek.security.account.service.AccountService;
import ru.kifor.kek.security.auth.JwtService;

@RestController
public class LoginController {
  @Autowired
  private AccountService accountService;

  @Autowired
  private JwtService jwtService;


  @PostMapping("/login")
  public String login(
      @RequestBody AccountLogin credentials
  ) {
    return jwtService.login(credentials);
  }

  @PostMapping("/registration")
  public boolean createAccount(
      @RequestBody AccountCreateModel accountModel
  ) {
    return accountService.create(accountModel);
  }

  @GetMapping("/213")
  public String test(Authentication authentication) {
    return "213";
  }
}
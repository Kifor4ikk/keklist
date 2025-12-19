package ru.kifor.kek.security.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kifor.kek.security.account.models.AccountLogin;
import ru.kifor.kek.security.account.models.AccountModel;
import ru.kifor.kek.security.account.models.AccountCreateModel;
import ru.kifor.kek.security.account.service.AccountService;
import ru.kifor.kek.security.auth.JwtService;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private JwtService jwtService;




  @GetMapping("/{id}")
  public AccountModel getById(
      @PathVariable Long id
  ){
    return accountService.getById(id);
  }

  @GetMapping("/")
  public AccountModel getByLogin(
      @RequestParam String login
  ){
    return accountService.getByLogin(login);
  }

  @PutMapping("/")
  public AccountModel chainWithPerson(
      @RequestParam Long accountId,
      @RequestParam Long personId
  ){
    return accountService.chainAccountAndPerson(accountId, personId);
  }


}

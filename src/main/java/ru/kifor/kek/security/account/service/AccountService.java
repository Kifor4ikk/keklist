package ru.kifor.kek.security.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kifor.kek.security.account.models.AccountCreateModel;
import ru.kifor.kek.security.account.models.AccountCredentials;
import ru.kifor.kek.security.account.models.AccountModel;
import ru.kifor.kek.security.account.models.AccountTokenModel;
import ru.kifor.kek.security.account.repository.AccountRepository;

import java.time.LocalDateTime;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  public boolean create(AccountCreateModel accountModelCreate) {
    return this.accountRepository.createAccount(accountModelCreate);
  }

  public AccountModel getById(Long id){
    return this.accountRepository.getAccountById(id);
  }

  public AccountModel getByLogin(String login){
    return this.accountRepository.getAccountByLogin(login);
  }

  public AccountModel chainAccountAndPerson(Long accountId, Long personId){
    return this.accountRepository.chainAccountAndPerson(accountId, personId);
  }

  public AccountTokenModel getTokenInfo(String login){
    return this.accountRepository.getTokenInfo(login);
  }

  public AccountTokenModel updateToken(String login, String token, LocalDateTime expiredAt) {
    return this.accountRepository.updateToken(login, token, expiredAt);
  }

  public boolean compareLogPass(String login, String password) {
    return this.accountRepository.compareLogPass(login, password);
  }

  public AccountCredentials getCredentials(String login) {
    return this.accountRepository.getCredentials(login);
  }

}

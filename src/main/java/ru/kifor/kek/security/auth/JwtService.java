package ru.kifor.kek.security.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kifor.kek.enums.Roles;
import ru.kifor.kek.security.account.models.AccountLogin;
import ru.kifor.kek.security.account.models.AccountModel;
import ru.kifor.kek.security.account.models.AccountTokenModel;
import ru.kifor.kek.security.account.service.AccountService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.json.*;
@Component
public class JwtService {

  private static final Logger log = LoggerFactory.getLogger(JwtService.class);
  @Value("${jwt-secret-key}")
  String jwtSecretKey;

  @Autowired
  AccountService accountService;

  public String generateToken(String login) {
    AccountModel account = accountService.getByLogin(login);

    Map<String, String> jwt = Map.of(
        "login", login,
        "role", String.valueOf(account.getRole()),
        "expiredAt", String.valueOf(LocalDateTime.now().plusDays(1))
    );
    JSONObject json = new JSONObject(jwt);
    return Base64.getEncoder().withoutPadding().encodeToString(json.toString().getBytes(StandardCharsets.UTF_8));
  }

  public AccountTokenModel fromBase64json(String value) throws JsonProcessingException {
    String clearString = Arrays.toString(Base64.getUrlDecoder().decode(value));

    HashMap map = new ObjectMapper().readValue(clearString, HashMap.class);

    return AccountTokenModel.builder()
        .login(map.get("login").toString())
        .role(Roles.valueOf(map.get("role").toString()))
        .expiredAt(LocalDateTime.parse(map.get("expiredAt").toString()))
        .token(value)
        .build();
  }

  public Boolean isJwtValid(AccountTokenModel tokenState) {
    AccountTokenModel currentTokenState = accountService.getTokenInfo(tokenState.getLogin());
    if(tokenState.getToken() == null)
      return false;
    if (currentTokenState.getExpiredAt() == null) return false;
    return tokenState.getToken().equals(currentTokenState.getToken()) &&
        currentTokenState.getExpiredAt().isAfter(LocalDateTime.now());
  }

  public String refreshToken(String login){
    var tokenInfo = accountService.getTokenInfo(login);
    if(isJwtValid(tokenInfo))
      return tokenInfo.getToken();
    else{
      var token = this.generateToken(login);
      accountService.updateToken(login, token, LocalDateTime.now().plusHours(8));
      return token;
    }
  }

  public String login(AccountLogin accountLogin) {
    if(this.accountService.compareLogPass(accountLogin.getLogin(), accountLogin.getPassword())){
      var token = accountService.getTokenInfo(accountLogin.getLogin());
      if(!isJwtValid(token)){
        System.out.println("New token");
        return this.refreshToken(accountLogin.getLogin());
      }
      else{
        System.out.println("Token is valid for " + token.getExpiredAt());
        return token.getToken();
      }
    }

    throw new RuntimeException("Wrong login or password");
  }
}

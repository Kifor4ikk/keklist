package ru.kifor.kek.security.account.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kifor.kek.enums.Roles;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.security.account.models.AccountCreateModel;
import ru.kifor.kek.security.account.models.AccountCredentials;
import ru.kifor.kek.security.account.models.AccountModel;
import ru.kifor.kek.security.account.models.AccountTokenModel;

import java.time.LocalDateTime;

import static org.jooq.impl.DSL.*;
import static ru.kifor.kek.Tables.PERSON;
import static ru.kifor.kek.tables.Account.ACCOUNT;
import static ru.kifor.kek.tables.Guild.GUILD;

@Repository
public class AccountRepository {

  @Autowired
  private DSLContext dslContext;

  public boolean createAccount(AccountCreateModel accountModel) {
    dslContext
        .insertInto(
            ACCOUNT,
            ACCOUNT.LOGIN,
            ACCOUNT.PASSWORD,
            ACCOUNT.ROLE
        )
        .values(
            accountModel.getLogin(),
            accountModel.getPassword(),
            Roles.USER
        ).execute();
    System.out.println(accountModel.getLogin() + " " + accountModel.getPassword());
    return true;
  }

  private String getAccountLoginById(Long id) {
    return dslContext.select(ACCOUNT.LOGIN).from(ACCOUNT).where(ACCOUNT.ID.eq(id)).fetchOne().value1();
  }

  public AccountModel getAccountById(Long id) {
    return getAccountByLogin(
        this.getAccountLoginById(id)
    );
  }

  public AccountModel getAccountByLogin(String login) {
    return dslContext
        .select(
            ACCOUNT,
            ACCOUNT.ID,
            ACCOUNT.LOGIN,
            ACCOUNT.ROLE,
            multiset(
                select(
                    PERSON,
                    PERSON.ID,
                    PERSON.NAME,
                    PERSON.SPEC,
                    PERSON.GEAR_SCORE,
                    multiset(
                        select(
                            GUILD,
                            GUILD.ID,
                            GUILD.NAME,
                            GUILD.OWNER_ID
                        ).from(GUILD)
                            .where(GUILD.ID.eq(PERSON.GUILD_ID))
                    ).as("guild")
                ).from(PERSON)
                    .where(PERSON.ID.eq(ACCOUNT.PERSONID))
            )
        )
        .from(ACCOUNT)
        .where(ACCOUNT.LOGIN.eq(login))
        .fetch().map(
            record -> AccountModel.builder()
                .id(record.get(ACCOUNT.ID))
                .login(record.get(ACCOUNT.LOGIN))
                .role(record.get(ACCOUNT.ROLE))
                .personModel(
//                    record.value5().map(
//                        person -> PersonModel.builder()
//                            .id(person.get(PERSON.ID))
//                            .name(person.get(PERSON.NAME))
//                            .spec(person.get(PERSON.SPEC))
//                            .gearScore(person.get(PERSON.GEAR_SCORE))
//                            .guild(
//                                null
////                                person.get("guild").map(
////                                    guild -> GuildModel.builder()
////                                        .id(guild.get(GUILD.ID))
////                                        .name(guild.get(GUILD.NAME))
////                                        .build()
////                                ).getFirst()
//                            )
//                            .build()
//                    )
                    null
                )
                .build()
        ).getFirst();
  }

  public AccountModel chainAccountAndPerson(Long accountId, Long personId) {
    dslContext
        .update(ACCOUNT)
        .set(ACCOUNT.PERSONID, personId)
        .where(ACCOUNT.ID.eq(accountId)).execute();
    return getAccountById(accountId);
  }


  public AccountTokenModel getTokenInfo(String login) {
    return dslContext
        .select(
            ACCOUNT.LOGIN,
            ACCOUNT.ROLE,
            ACCOUNT.PERSONID,
            ACCOUNT.TOKEN,
            ACCOUNT.VALID_TOKEN_TIME
        )
        .from(ACCOUNT)
        .where(ACCOUNT.LOGIN.eq(login))
        .fetch().map(
            r -> AccountTokenModel.builder()
                .login(r.get(ACCOUNT.LOGIN))
                .role(r.get(ACCOUNT.ROLE))
                .personId(r.get(ACCOUNT.PERSONID))
                .token(r.get(ACCOUNT.TOKEN))
                .expiredAt(r.get(ACCOUNT.VALID_TOKEN_TIME))
                .build()
        ).getFirst();
  }

  public AccountTokenModel updateToken(String login, String token, LocalDateTime expiredAt){
    dslContext.update(ACCOUNT)
        .set(ACCOUNT.TOKEN, token)
        .set(ACCOUNT.VALID_TOKEN_TIME, expiredAt)
        .where(ACCOUNT.LOGIN.eq(login))
        .execute();
    return getTokenInfo(login);
  }


  public boolean compareLogPass(String login, String password){
    var x = dslContext.selectCount().from(ACCOUNT).where(ACCOUNT.LOGIN.eq(login)).and(ACCOUNT.PASSWORD.eq(password)).fetchOne().value1();
    return x > 0;
  }

  public AccountCredentials getCredentials(String login){
    System.out.println("LOGIN <" + login + ">");
    var x = dslContext.select(
            ACCOUNT.LOGIN,
            ACCOUNT.PASSWORD,
            ACCOUNT.ROLE
    ).from(ACCOUNT).where(ACCOUNT.LOGIN.eq(login)).fetch().map(
        r -> AccountCredentials.builder()
            .login(r.get(ACCOUNT.LOGIN))
            .password(r.get(ACCOUNT.PASSWORD))
            .role(r.get(ACCOUNT.ROLE))
            .build()
    );
    if( !x.isEmpty())
      return x.getFirst();
    throw new RuntimeException("No such user");
  }
}


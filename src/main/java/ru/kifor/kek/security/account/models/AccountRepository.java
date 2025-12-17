package ru.kifor.kek.security.account.models;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.person.model.PersonModel;

import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;
import static ru.kifor.kek.Tables.PERSON;
import static ru.kifor.kek.tables.Account.ACCOUNT;
import static ru.kifor.kek.tables.Guild.GUILD;

public class AccountRepository {

  @Autowired
  private DSLContext dslContext;

  private void createAccount(AccountModelCreate accountModel) {
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
            accountModel.getRole()
        )
        .execute();
  }

  private AccountModel getAccount(String login){
    return dslContext
        .select(
            ACCOUNT,
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
                      )
                          .where(GUILD.ID.eq(PERSON.GUILD_ID))
                  )
              )
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
                    record.value4().map(
                        person -> PersonModel.builder()
                            .id(person.get(PERSON.ID))
                            .name(person.get(PERSON.NAME))
                            .spec(person.get(PERSON.SPEC))
                            .gearScore(person.get(PERSON.GEAR_SCORE))
                            .guild(
                                person.value6().map(
                                    guild -> GuildModel.builder()
                                        .id(guild.get(GUILD.ID))
                                        .name(guild.get(GUILD.NAME))
                                        .build()
                                ).getFirst()
                            )
                            .build()
                    ).getFirst()
                )
                .build()
        ).getFirst();
  }
//  private AccountModel chainAccountAndPerson(Long accountId, Long personId){
//    return dslContext
//        .update(ACCOUNT)
//        .set(ACCOUNT.PERSONID, personId)
//        .where(ACCOUNT.ID.eq(accountId)).execute();
//  }
}


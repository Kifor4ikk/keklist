package ru.kifor.kek.invite.repository;

import lombok.*;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.base.model.BaseUpdateModel;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.invite.model.InviteCreateModel;
import ru.kifor.kek.invite.model.InviteFilterModel;
import ru.kifor.kek.invite.model.InviteModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.person.model.PersonUpdateModel;
import ru.kifor.kek.person.service.PersonService;
import ru.kifor.kek.tables.Invites;
import ru.kifor.kek.utils.AlreadyInGuildException;
import ru.kifor.kek.utils.NotFoundException;

import java.util.*;

import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;
import static ru.kifor.kek.tables.Guild.GUILD;
import static ru.kifor.kek.tables.Invites.INVITES;
import static ru.kifor.kek.tables.Person.PERSON;

@Repository
@AllArgsConstructor
@RequiredArgsConstructor
public class InviteRepository extends BaseRepositoryImpl<
    Invites,
    InviteCreateModel,
    InviteModel,
    BaseUpdateModel,
    InviteFilterModel> {

  @Autowired
  protected DSLContext dslContext;

  @Autowired
  private PersonService personService;


  public InviteModel create(InviteCreateModel inviteModel){

    var id = dslContext.insertInto(
        INVITES,
        INVITES.PERSON_ID,
        INVITES.GUILD_ID
    ).values(
        inviteModel.getPersonId(),
        inviteModel.getGuildId()
    ).returning(INVITES.GUILD_ID).fetchOne(0, Long.class);
    return getById(id);
  }

  public InviteModel getById(Long id){
    var result = dslContext.select(
        INVITES.ID,
        INVITES.CREATE_DATE,
        INVITES.ISPROCESSED,
        INVITES.RESULT,
        multiset(
            select(
                PERSON.ID,
                PERSON.NAME,
                PERSON.GEAR_SCORE,
                PERSON.SPEC,
                PERSON.CREATE_DATE
            ).from(PERSON)
                .join(INVITES).on(PERSON.ID.eq(INVITES.PERSON_ID))
                .where(INVITES.ID.eq(id))
        ),
        multiset(
            select(
                GUILD.ID,
                GUILD.NAME,
                GUILD.CREATE_DATE
            ).from(GUILD)
                .join(INVITES).on(INVITES.GUILD_ID.eq(GUILD.ID))
                .where(INVITES.ID.eq(id))
        )
    ).from(INVITES)
        .where(INVITES.ID.eq(id))
        .fetch().map(r -> (InviteModel) InviteModel.builder()
            .id(r.get(INVITES.ID))
            .createdAt(r.get(INVITES.CREATE_DATE).atTime(0,0))
            .isProcessed(r.get(INVITES.ISPROCESSED))
            .result(r.get(INVITES.RESULT))
            .guild(
                r.value5().map(
                    guild -> (GuildModel) GuildModel.builder()
                        .id(guild.get(GUILD.ID))
                        .name(guild.get(GUILD.NAME))
                        .createdAt(guild.get(GUILD.CREATE_DATE).atTime(0,0))
                        .build()
                ).getFirst()
            )
            .person(
                r.value6().map(
                    person -> (PersonModel) PersonModel.builder()
                        .id(person.get(PERSON.ID))
                        .name(person.get(PERSON.NAME))
                        .gearScore(person.get(PERSON.GEAR_SCORE))
                        .spec(person.get(PERSON.SPEC))
                        .createdAt(person.get(PERSON.CREATE_DATE).atTime(0,0))
                        .build()
                ).getFirst()
            )
            .build()
        );

    if(!result.isEmpty())
      return result.getFirst();
    throw new NotFoundException("Invite with id " + id + " not found");
  }

  @Override
  public InviteModel update(Long id, BaseUpdateModel updateModel) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void delete(Long id) {
    super.delete(id);
  }

  @Override
  public BasePageble<InviteModel> getAll(InviteFilterModel filterModel) {

    List<Condition> conditions = new ArrayList<>();

    if (filterModel.getPersonId().isPresent())
      conditions.add(INVITES.PERSON_ID.eq(filterModel.getPersonId().get()));
    if (filterModel.getGuildId().isPresent())
      conditions.add(INVITES.GUILD_ID.eq(filterModel.getGuildId().get()));
    if (filterModel.getIsProcessed().isPresent())
      conditions.add(INVITES.ISPROCESSED.eq(filterModel.getIsProcessed().get()));
    if (filterModel.getResult().isPresent())
      conditions.add(INVITES.RESULT.eq(filterModel.getResult().get()));

    if(filterModel.getDateMin().isPresent())
      conditions.add(GUILD.CREATE_DATE.greaterOrEqual(filterModel.getDateMin().get()));
    if(filterModel.getDateMax().isPresent())
      conditions.add(GUILD.CREATE_DATE.lessOrEqual(filterModel.getDateMax().get()));


    var data =  dslContext.select(
            INVITES.ID,
            INVITES.CREATE_DATE,
            INVITES.ISPROCESSED,
            INVITES.RESULT,
            multiset(
                select(
                    PERSON.ID,
                    PERSON.NAME,
                    PERSON.GEAR_SCORE,
                    PERSON.SPEC,
                    PERSON.CREATE_DATE
                ).from(PERSON).join(INVITES).on(PERSON.ID.eq(INVITES.PERSON_ID))
            ),
            multiset(
                select(
                    GUILD.ID,
                    GUILD.NAME,
                    GUILD.CREATE_DATE
                ).from(GUILD).join(INVITES).on(INVITES.GUILD_ID.eq(GUILD.ID))
            )
        ).from(INVITES)
        .where(conditions)
        .fetch().map(r -> (InviteModel) InviteModel.builder()
            .id(r.get(INVITES.ID))
            .createdAt(r.get(INVITES.CREATE_DATE).atTime(0,0))
            .isProcessed(r.get(INVITES.ISPROCESSED))
            .result(r.get(INVITES.RESULT))
            .guild(
                r.value5().map(
                    guild -> (GuildModel) GuildModel.builder()
                        .id(guild.get(GUILD.ID))
                        .name(guild.get(GUILD.NAME))
                        .createdAt(guild.get(GUILD.CREATE_DATE).atTime(0,0))
                        .build()
                ).getFirst()
            )
            .person(
                r.value6().map(
                    person -> (PersonModel) PersonModel.builder()
                        .id(person.get(PERSON.ID))
                        .name(person.get(PERSON.NAME))
                        .gearScore(person.get(PERSON.GEAR_SCORE))
                        .spec(person.get(PERSON.SPEC))
                        .createdAt(person.get(PERSON.CREATE_DATE).atTime(0,0))
                        .build()
                ).getFirst()
            )
            .build()
        );
    var count = dslContext.selectCount().from(INVITES).where(conditions).fetchOne(0, Integer.class);
    return new BasePageble<InviteModel>(
        filterModel.getPage(),
        filterModel.getLimit(),
        count,
        data
    );
  }

  public boolean acceptInvite(Long id){
    var invite = this.getById(id);
    PersonModel person = personService.getById(invite.getPerson().getId());
    if(person.getGuild() == null)
      throw new AlreadyInGuildException("Уже состоит в гильдии");
    Map<Field<?>, Object> map = new HashMap<>();

    dslContext
        .update(INVITES)
        .set(
            Map.of(
                INVITES.ISPROCESSED, true,
                INVITES.RESULT, true
            )
        )
        .where(INVITES.ID.eq(id));

    personService.update(invite.getPerson().getId(),
        PersonUpdateModel
            .builder()
            .guildId(Optional.of(invite.getGuild().getId()))
            .build()
    );
    return true;
  }
}

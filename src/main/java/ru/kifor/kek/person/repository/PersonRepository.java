package ru.kifor.kek.person.repository;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.LikeEscapeStep;
import org.springframework.stereotype.Repository;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.base.repository.BaseRepository;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.person.model.PersonCreateModel;
import ru.kifor.kek.person.model.PersonFilterModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.person.model.PersonUpdateModel;
import ru.kifor.kek.tables.Guild;
import ru.kifor.kek.tables.Person;
import ru.kifor.kek.utils.NotFoundException;

import static org.jooq.impl.DSL.*;
import static ru.kifor.kek.tables.Person.PERSON;

import java.util.*;

import static ru.kifor.kek.tables.Guild.GUILD;

@Repository
public class PersonRepository
    extends BaseRepositoryImpl<Person, PersonCreateModel, PersonModel, PersonUpdateModel, PersonFilterModel> {

  @Override
  public PersonModel create(PersonCreateModel createModel) {
    return (PersonModel) dslContext
        .insertInto(
            PERSON,
            PERSON.NAME,
            PERSON.GEAR_SCORE,
            PERSON.SPEC
        )
        .values(
            createModel.getName(),
            createModel.getGearScore(),
            createModel.getSpec()
        ).returningResult(
            PERSON.ID,
            PERSON.NAME,
            PERSON.GEAR_SCORE,
            PERSON.SPEC,
            PERSON.CREATE_DATE
        ).fetch().map(
            record -> PersonModel.builder()
                .id(record.value1())
                .name(record.value2())
                .gearScore(record.value3())
                .spec(record.value4())
                .createdAt(record.value5().atTime(0,0))
                .build()
        ).getFirst();
  }

  @Override
  public PersonModel getById(Long id) {
    var result = dslContext.select(
        PERSON.ID,
        PERSON.NAME,
        PERSON.GEAR_SCORE,
        PERSON.SPEC,
        PERSON.CREATE_DATE,
        GUILD.ID,
        GUILD.NAME,
        GUILD.CREATE_DATE
    ).from(PERSON)
        .join(GUILD)
        .on(GUILD.ID.eq(PERSON.GUILD_ID))
        .where(PERSON.ID.eq(id))
        .fetch().map(
            record -> PersonModel.builder()
                .id(record.get(PERSON.ID))
                .name(record.get(PERSON.NAME))
                .gearScore(record.get(PERSON.GEAR_SCORE))
                .spec(record.get(PERSON.SPEC))
                .createdAt(record.get(PERSON.CREATE_DATE).atTime(0,0))
                .guild(GuildModel.builder()
                    .id(record.get(GUILD.ID))
                    .name(record.get(GUILD.NAME))
                    .createdAt(record.get(GUILD.CREATE_DATE).atTime(0,0))
                    .build()
                )
                .build()
        );

    if(!result.isEmpty())
      return result.getFirst();
    throw new NotFoundException("Invite with id " + id + " not found");
  }

  @Override
  public PersonModel update(Long id, PersonUpdateModel updateModel) {
    Map<Field<?>, Object> map = new HashMap<>();

    if(updateModel.getGearScore().isPresent())
      map.put(PERSON.GEAR_SCORE, updateModel.getGearScore());
    if(updateModel.getName().isPresent())
      map.put(PERSON.NAME, updateModel.getName());
    if(updateModel.getSpec().isPresent())
      map.put(PERSON.SPEC, updateModel.getSpec());
    if(updateModel.getGuildId().isPresent())
      map.put(PERSON.GUILD_ID, updateModel.getGuildId());

    dslContext.update(PERSON)
        .set(map)
        .where(PERSON.ID.eq(id))
        .execute();

    return getById(id);
  }

  @Override
  public void delete(Long id) {
    dslContext.deleteFrom(PERSON)
        .where(PERSON.ID.eq(id))
        .execute();
  }

  @Override
  public BasePageble<PersonModel> getAll(PersonFilterModel filterModel) {
    List<Condition> conditions = new ArrayList<>();

    if (filterModel.getName().isPresent())
      conditions.add(PERSON.NAME.like(filterModel.getName().get()));
    if (!filterModel.getSpec().isEmpty())
      conditions.add(PERSON.SPEC.in(filterModel.getSpec()));
    if (filterModel.getGuildId().isPresent())
      conditions.add(PERSON.GUILD_ID.eq(filterModel.getGuildId().get()));
    if (filterModel.getGearMin().isPresent())
      conditions.add(PERSON.GEAR_SCORE.ge(filterModel.getGearMin().get()));
    if (filterModel.getGearMax().isPresent())
      conditions.add(PERSON.GEAR_SCORE.le(filterModel.getGearMax().get()));

    if(filterModel.getDateMin().isPresent())
      conditions.add(PERSON.CREATE_DATE.greaterOrEqual(filterModel.getDateMin().get()));
    if(filterModel.getDateMax().isPresent())
      conditions.add(PERSON.CREATE_DATE.lessOrEqual(filterModel.getDateMax().get()));

    var result = dslContext.select(
            PERSON.ID,
            PERSON.NAME,
            PERSON.GEAR_SCORE,
            PERSON.SPEC,
            PERSON.CREATE_DATE,
            GUILD.ID,
            GUILD.NAME,
            GUILD.CREATE_DATE
        ).from(PERSON)
        .join(GUILD)
        .on(GUILD.ID.eq(PERSON.GUILD_ID))
        .offset(filterModel.getPage() * filterModel.getLimit())
        .limit(filterModel.getLimit())
        .fetch().map(
            record -> (PersonModel) PersonModel.builder()
                .id(record.get(PERSON.ID))
                .name(record.get(PERSON.NAME))
                .gearScore(record.get(PERSON.GEAR_SCORE))
                .spec(record.get(PERSON.SPEC))
                .createdAt(record.get(PERSON.CREATE_DATE).atTime(0,0))
                .guild(GuildModel.builder()
                    .id(record.get(GUILD.ID))
                    .name(record.get(GUILD.NAME))
                    .createdAt(record.get(GUILD.CREATE_DATE).atTime(0,0))
                    .build()
                )
                .build()
        );

    var count = dslContext.select(count(PERSON.ID)).from(PERSON).where(conditions).fetchOne(0, Integer.class);

    return  new BasePageble<PersonModel>(
            filterModel.getPage(),
            filterModel.getLimit(),
            count,
            result
    );

  }

  public boolean leaveGuild(Long personId) {
    dslContext.update(PERSON)
        .setNull(PERSON.GUILD_ID)
        .where(PERSON.ID.eq(personId));
    return true;
  }

}

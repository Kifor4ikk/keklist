package ru.kifor.kek.guild.repository;

import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.LikeEscapeStep;
import org.springframework.stereotype.Repository;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.guild.model.GuildCreateModel;
import ru.kifor.kek.guild.model.GuildFilterModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.guild.model.GuildUpdateModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.tables.Guild;
import ru.kifor.kek.utils.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.*;
import static ru.kifor.kek.tables.Guild.GUILD;
import static ru.kifor.kek.tables.Person.PERSON;

@Repository
@AllArgsConstructor
public class GuildRepository
    extends BaseRepositoryImpl<Guild, GuildCreateModel, GuildModel, GuildUpdateModel, GuildFilterModel> {

  @Override
  public GuildModel create(GuildCreateModel createModel) {
    System.out.println(createModel);
    return dslContext
        .insertInto(
            GUILD,
            GUILD.NAME
        )
        .values(createModel.getName())
        .returningResult(
            GUILD.ID,
            GUILD.CREATE_DATE,
            GUILD.NAME
        ).fetch().map(
            record -> GuildModel.builder()
                .name(record.value3())
                .members(List.of())
                .createdAt(record.value2().atTime(0,0))
                .id(record.value1())
                .build()
        ).getFirst();
  }

  @Override
  public GuildModel getById(Long id) {
    var result = dslContext.select(
            GUILD.ID,
            GUILD.NAME,
            GUILD.CREATE_DATE,
            multiset(
                select
                    (
                        PERSON.ID,
                        PERSON.GUILD_ID,
                        PERSON.NAME,
                        PERSON.GEAR_SCORE,
                        PERSON.SPEC,
                        PERSON.CREATE_DATE,
                        PERSON.UPDATE_DATE
                    )
                    .from(PERSON)
                    .where(PERSON.GUILD_ID.eq(GUILD.ID))
            ).as("members"),
            multiset(
                select
                    (
                        PERSON.ID,
                        PERSON.GUILD_ID,
                        PERSON.NAME,
                        PERSON.GEAR_SCORE,
                        PERSON.SPEC,
                        PERSON.CREATE_DATE,
                        PERSON.UPDATE_DATE
                    )
                    .from(PERSON)
                    .where(PERSON.GUILD_ID.eq(GUILD.ID), GUILD.OWNER_ID.eq(PERSON.ID))
            ).as("owner")
        ).from(GUILD)
        .where(GUILD.ID.eq(id))
        .fetch().map(
            record ->
              GuildModel.builder()
                  .id(record.get(GUILD.ID))
                  .name(record.get(GUILD.NAME))
                  .createdAt(record.get(GUILD.CREATE_DATE).atTime(0, 0))
                  .members(
                      record.value4().stream()
                          .map(pers -> PersonModel
                              .builder()
                              .id(pers.get(PERSON.ID))
                              .name(pers.get(PERSON.NAME))
                              .gearScore(pers.get(PERSON.GEAR_SCORE))
                              .spec(pers.get(PERSON.SPEC))
                              .createdAt(pers.get(PERSON.CREATE_DATE).atTime(0, 0))
                              .build()
                          ).collect(toList())
                  )
                  .owner(
                      record.value5() == null ? null :
                          record.value5().stream().map(
                              owner -> PersonModel.builder()
                                  .id(owner.get(PERSON.ID))
                                  .name(owner.get(PERSON.NAME))
                                  .gearScore(owner.get(PERSON.GEAR_SCORE))
                                  .spec(owner.get(PERSON.SPEC))
                                  .createdAt(owner.get(PERSON.CREATE_DATE).atTime(0, 0))
                                  .build()
                          ).findAny().orElse(null)
                  )
                  .build()
        );

    if(!result.isEmpty())
      return result.getFirst();
    throw new NotFoundException("Guild with id " + id + " not found");
  }

  @Override
  public GuildModel update(Long id, GuildUpdateModel updateModel) {
    dslContext.update(GUILD)
        .set(GUILD.NAME, updateModel.getName())
        .where(GUILD.ID.eq(id))
        .execute();
    return this.getById(id);
  }

  @Override
  public void delete(Long id) {
    dslContext.deleteFrom(GUILD)
        .where(GUILD.ID.eq(id))
        .execute();
  }

  @Override
  public BasePageble<GuildModel> getAll(GuildFilterModel filterModel) {
    List<Condition> conditions = new ArrayList<>();
    if (filterModel.getName() != null)
      conditions.add(GUILD.NAME.likeIgnoreCase("%" + filterModel.getName() + "%"));

    var resultList = dslContext.select(
        GUILD.ID,
        GUILD.NAME,
        GUILD.CREATE_DATE
    )
        .from(GUILD)
        .where(conditions)
        .limit(filterModel.getLimit())
        .offset(filterModel.getPage() * filterModel.getLimit())
        .fetch()
        .map(
            record -> (GuildModel) GuildModel.builder()
            .id(record.value1())
            .name(record.value2())
            .createdAt(record.value3().atTime(0, 0))
            .build()
        );
    var total = dslContext.select(count(GUILD.ID)).from(GUILD).where(conditions).fetchOne(0, int.class);
    return new BasePageble<GuildModel>(filterModel.getPage(), filterModel.getLimit(), total, resultList);
  }

}

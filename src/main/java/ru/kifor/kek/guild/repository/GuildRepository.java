package ru.kifor.kek.guild.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.guild.model.GuildCreateModel;
import ru.kifor.kek.guild.model.GuildFilterModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.guild.model.GuildUpdateModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.tables.Guild;
import ru.kifor.kek.utils.NotFoundException;

import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.*;
import static ru.kifor.kek.tables.Guild.GUILD;
import static ru.kifor.kek.tables.Person.PERSON;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            ).as("members")
        ).from(GUILD)
        .where(GUILD.ID.eq(id))
        .fetch().map(
            record ->
              GuildModel.builder()
                  .id(record.value1())
                  .name(record.value2())
                  .createdAt(record.value3().atTime(0, 0))
                  .members(
                      record.value4().stream()
                          .map(pers -> PersonModel
                              .builder()
                              .id(pers.value1())
                              .guildId(pers.value2())
                              .name(pers.value3())
                              .gearScore(pers.value4())
                              .spec(pers.value5())
                              .createdAt(pers.value6().atTime(0, 0))
                              .build()
                          ).collect(toList())
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
  public List<GuildModel> getAll(GuildFilterModel filterModel) {
    return dslContext
        .select(
            GUILD.ID,
            GUILD.NAME,
            GUILD.CREATE_DATE
        ).from(GUILD)
        .offset(filterModel.getPage())
        .limit(filterModel.getLimit())
        .fetch().map(
            record -> GuildModel.builder()
                .id(record.value1())
                .name(record.value2())
                .createdAt(record.value3().atTime(0, 0))
                .build()
        );
  }
}

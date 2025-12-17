package ru.kifor.kek.event.repository;

import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.event.model.*;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.tables.Event;
import ru.kifor.kek.tables.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.*;
import static ru.kifor.kek.tables.Event.EVENT;
import static ru.kifor.kek.tables.Guild.GUILD;
import static ru.kifor.kek.tables.Person.PERSON;
import static ru.kifor.kek.tables.Eventtoperson.EVENTTOPERSON;

@Repository
@AllArgsConstructor
public class EventRepository extends BaseRepositoryImpl
    <Event, EventCreateModel, EventModel, EventUpdateModel, EventFilterModel> {

  @Override
  public EventModel create(EventCreateModel createModel) {
    var id = dslContext
        .insertInto(
            EVENT,
            EVENT.NAME,
            EVENT.GOLD,
            EVENT.CREATE_DATE,
            EVENT.GUILD_ID
        )
        .values(
            createModel.getName(),
            createModel.getGold(),
            LocalDate.from(java.time.LocalDateTime.now()),
            createModel.getGuildId()
        )
        .returningResult(EVENT.ID)
        .fetch().map(rec -> rec.get(EVENT.ID)).getFirst();
    return this.getById(id);
  }


  @Override
  public EventModel getById(Long id) {
    return dslContext
        .select(
            EVENT.ID,
            EVENT.NAME,
            EVENT.GOLD,
            EVENT.CREATE_DATE,
            multiset(
                select(
                    GUILD.ID,
                    GUILD.NAME
                )
                    .from(GUILD)
                    .where(GUILD.ID.eq(EVENT.GUILD_ID))
            ).as("guild"),
            multiset(
                select(
                    PERSON.ID,
                    PERSON.NAME,
                    PERSON.SPEC,
                    PERSON.GEAR_SCORE,
                    PERSON.GUILD_ID
                )
                    .from(PERSON)
                    .join(EVENTTOPERSON).on(PERSON.ID.eq(EVENTTOPERSON.PERSON_ID))
                    .where(EVENTTOPERSON.EVENT_ID.eq(id))
            ).as("person")
        ).from(EVENT)
        .where(EVENT.ID.eq(id))
        .fetch().map(
            rec -> (EventModel) new EventModel().builder()
                .id(rec.get(EVENT.ID))
                .name(rec.get(EVENT.NAME))
                .gold(rec.get(EVENT.GOLD))
                .createdAt(rec.get(EVENT.CREATE_DATE).atTime(0, 0))
                .guild(rec.value5().map(
                            rec1 -> (GuildModel) new GuildModel().builder()
                                .id(rec1.get(GUILD.ID))
                                .name(rec1.get(GUILD.NAME))
                                .owner(null)
                                .members(null)
                                .build()
                        )
                        .getFirst()
                )
                .persons(
                    rec.value6().map(
                        rec2 -> (PersonModel) new PersonModel().builder()
                            .id(rec2.get(PERSON.ID))
                            .name(rec2.get(PERSON.NAME))
                            .spec(rec2.get(PERSON.SPEC))
                            .gearScore(rec2.get(PERSON.GEAR_SCORE))
                            .guild(null)
                            .build()
                    )
                )
                .build()

        ).getFirst();
  }

  @Override
  public EventModel update(Long id, EventUpdateModel updateModel) {
    Map<Field<?>, Object> map = new HashMap<>();

    if (updateModel.getName().isPresent())
      map.put(EVENT.NAME, updateModel.getName());
    if (updateModel.getGold().isPresent())
      map.put(EVENT.GOLD, updateModel.getGold());

    dslContext.update(EVENT)
        .set(map)
        .where(EVENT.ID.eq(id));
    return this.getById(id);
  }

  public void delete(Long id) {
    dslContext.delete(EVENT)
        .where(EVENT.ID.eq(id))
        .execute();
  }

  public BasePageble<EventModel> getAll(EventFilterModel filterModel) {
    List<Condition> conditions = new ArrayList<>();
    // Дата
    if(filterModel.getDateMin().isPresent())
      conditions.add(EVENT.CREATE_DATE.greaterOrEqual(filterModel.getDateMin().get()));
    if(filterModel.getDateMax().isPresent())
      conditions.add(EVENT.CREATE_DATE.lessOrEqual(filterModel.getDateMax().get()));
    // Другое
    if (filterModel.getGuildId().isPresent())
      conditions.add(EVENT.GUILD_ID.eq(filterModel.getGuildId().get()));
    if (filterModel.getName().isPresent())
      conditions.add(EVENT.NAME.likeIgnoreCase("%" + filterModel.getName() + "%"));
    // Голда
    if (filterModel.getMinGold().isPresent())
      conditions.add(EVENT.GOLD.greaterOrEqual(filterModel.getMinGold().get()));
    if (filterModel.getMaxGold().isPresent())
      conditions.add(EVENT.GOLD.lessOrEqual(filterModel.getMaxGold().get()));


    var listResult = dslContext.select(
            EVENT.ID,
            EVENT.NAME,
            EVENT.GOLD,
            EVENT.CREATE_DATE,
            multiset(
                select(
                    GUILD.ID,
                    GUILD.NAME
                )
                    .from(GUILD)
                    .where(GUILD.ID.eq(EVENT.GUILD_ID))
            ).as("guild")
        )
        .from(EVENT)
        .where(conditions)
        .offset(filterModel.getLimit() * filterModel.getPage())
        .limit(filterModel.getLimit())
        .fetch().map(
            rec -> (EventModel) EventModel.builder()
                .id(rec.get(EVENT.ID))
                .name(rec.get(EVENT.NAME))
                .gold(rec.get(EVENT.GOLD))
                .createdAt(rec.get(EVENT.CREATE_DATE).atTime(0,0))
                .guild(rec.value5().map(
                            rec1 -> (GuildModel) new GuildModel().builder()
                                .id(rec1.get(GUILD.ID))
                                .name(rec1.get(GUILD.NAME))
                                .owner(null)
                                .members(null)
                                .build()
                        ).getFirst()
                )
                .build()
        );

    var count = dslContext.selectCount().from(EVENT).where(conditions).fetchOne(0, int.class);

    return new BasePageble<EventModel>(
      filterModel.getPage(), filterModel.getLimit(), count, listResult
    );

  }

  public Boolean addPersonToEvent(EventToPerson eventToPerson, boolean isAdd){
    if(isAdd)
      dslContext.insertInto(
          EVENTTOPERSON,
          EVENTTOPERSON.PERSON_ID,
          EVENTTOPERSON.EVENT_ID
      ).values(
          eventToPerson.getPersonId(),
          eventToPerson.getEventId()
      );
    else
      dslContext.deleteFrom(EVENTTOPERSON)
          .where(
              EVENTTOPERSON.PERSON_ID.eq(eventToPerson.getPersonId()),
              EVENTTOPERSON.EVENT_ID.eq(eventToPerson.getEventId())
          );
    return true;
  }
}
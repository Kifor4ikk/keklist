package ru.kifor.kek.event.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.event.model.EventCreateModel;
import ru.kifor.kek.event.model.EventFilterModel;
import ru.kifor.kek.event.model.EventModel;
import ru.kifor.kek.event.model.EventUpdateModel;
import ru.kifor.kek.guild.model.GuildCreateModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.tables.Event;

import java.time.LocalDate;
import java.util.List;

import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;
import static ru.kifor.kek.tables.Event.EVENT;
import static ru.kifor.kek.tables.Guild.GUILD;

@Repository
@AllArgsConstructor
public class EventRepository extends BaseRepositoryImpl
    <Event, EventCreateModel, EventModel, EventUpdateModel, EventFilterModel>  {

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
            ).as("guild")
        ).from(EVENT)
        .where(EVENT.ID.eq(id))
        .fetch().map(
                rec -> (EventModel) new EventModel().builder()
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
                        )
                        .getFirst()
                    ).build()
        ).getFirst();
  }

//  @Override
//  public EventModel update(Long id, EventUpdateModel updateModel) {
//
//  }
//    return dslContext.update(EVENT)
//        .set(EVENT.NAME, updateModel.getName())
//        .where(EVENT.ID.eq(id));

}

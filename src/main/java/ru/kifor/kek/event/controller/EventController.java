package ru.kifor.kek.event.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.event.model.EventCreateModel;
import ru.kifor.kek.event.model.EventFilterModel;
import ru.kifor.kek.event.model.EventModel;
import ru.kifor.kek.event.model.EventUpdateModel;
import ru.kifor.kek.event.service.EventService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {

  @Autowired
  private EventService service;

  @PostMapping("/")
  public EventModel create(
      @RequestBody EventCreateModel createModel
  ){
    return service.create(createModel);
  }

  @GetMapping("/{id}")
  public EventModel getById(
      @PathVariable(name="id") Long id
  ){
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public EventModel update(
      @PathVariable(name="id") Long id,
      @RequestBody EventUpdateModel schemaUpdate
  ){
    return service.update(id, schemaUpdate);
  }

  @DeleteMapping("/{id}")
  public void delete(
      @PathVariable(name="id") Long id){
    service.delete(id);
  }

  @GetMapping("/all")
  public BasePageble<EventModel> getAll(
      @RequestParam(required = false, name = "name") String name,

      @RequestParam(required = false, name = "minGold") Integer minGold,
      @RequestParam(required = false, name = "maxGold") Integer maxGold,

      @RequestParam(required = false, name = "guildId") Long guildId,

      @RequestParam(required = false, name = "minDate") LocalDate minDate,
      @RequestParam(required = false, name = "maxDate") LocalDate maxDate,

      @RequestParam(required = false, name = "page", defaultValue = "0") int page,
      @RequestParam(required = false, name = "limit", defaultValue = "10") int limit
  ){
    return service.getAll(
        EventFilterModel.builder()
            .name(Optional.ofNullable(name))
            .minGold(Optional.ofNullable(minGold))
            .maxGold(Optional.ofNullable(maxGold))
            .guildId(Optional.ofNullable(guildId))
            .dateMin(Optional.ofNullable(minDate))
            .dateMax(Optional.ofNullable(maxDate))
            .page(page)
            .limit(limit)
            .build()
    );
  }

}

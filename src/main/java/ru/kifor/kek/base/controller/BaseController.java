package ru.kifor.kek.base.controller;

import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kifor.kek.base.model.BaseCreateModel;
import ru.kifor.kek.base.model.BaseFilterModel;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.base.model.BaseUpdateModel;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.base.service.BaseService;
import ru.kifor.kek.guild.model.GuildFilterModel;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public abstract class BaseController<
    C extends BaseCreateModel,
    G extends BaseModel,
    U extends BaseUpdateModel,
    F extends BaseFilterModel,
    S extends BaseService<C, G, U, F>
    >
{

  @Autowired
  protected S service;

  @PostMapping("/")
  public G create(@RequestBody C createModel){
    return service.create(createModel);
  }

  @GetMapping("/{id}")
  public G getById(@PathVariable(name="id") Long id){
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public G update(@PathVariable(name="id") Long id, @RequestBody U schemaUpdate){
    return service.update(id, schemaUpdate);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(name="id") Long id){
    service.delete(id);
  }

  @GetMapping("/all")
  public List<G> getAll(
      @RequestParam(required = false, name = "page", defaultValue = "0") int page,
      @RequestParam(required = false, name = "limit", defaultValue = "10") int limit
      ){
    System.out.println(page);
    System.out.println(limit);
    var x = F.builder()
        .page(page)
        .limit(limit)
        .build();

    return service.getAll((F) x);
  }

}

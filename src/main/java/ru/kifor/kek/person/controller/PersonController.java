package ru.kifor.kek.person.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.enums.Spec;
import ru.kifor.kek.person.model.PersonCreateModel;
import ru.kifor.kek.person.model.PersonFilterModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.person.model.PersonUpdateModel;
import ru.kifor.kek.person.service.PersonService;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
  @Autowired
  private PersonService service;

  @PostMapping("/")
  public PersonModel create(
      @RequestBody PersonCreateModel createModel
  ){
    return service.create(createModel);
  }

  @GetMapping("/{id}")
  public PersonModel getById(
      @PathVariable(name="id") Long id
  ){
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public PersonModel update(
      @PathVariable(name="id") Long id,
      @RequestBody PersonUpdateModel schemaUpdate
  ){
    return service.update(id, schemaUpdate);
  }

  @DeleteMapping("/{id}")
  public void delete(
      @PathVariable(name="id") Long id){
    service.delete(id);
  }

  @GetMapping("/all")
  public BasePageble<PersonModel> getAll(
      @RequestParam(required = false, name = "name") String name,
      @RequestParam(required = false, name = "gearMin") int gearMin,
      @RequestParam(required = false, name = "gearMax") int gearMax,
      @RequestParam(required = false, name = "guildId") long guildId,
      @RequestParam(required = false, name = "specs") List<Spec> specs,

      @RequestParam(required = false, name = "page", defaultValue = "0") int page,
      @RequestParam(required = false, name = "limit", defaultValue = "10") int limit
  ){

    return service.getAll(
        PersonFilterModel.builder()
            .name(Optional.ofNullable(name))
            .gearMin(Optional.of(gearMin))
            .gearMax(Optional.of(gearMax))
            .guildId(Optional.of(guildId))
            .spec(specs)
            .page(page)
            .limit(limit)
            .build()
    );
  }

}
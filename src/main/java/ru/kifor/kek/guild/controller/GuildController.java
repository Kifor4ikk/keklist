package ru.kifor.kek.guild.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.guild.model.GuildCreateModel;
import ru.kifor.kek.guild.model.GuildFilterModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.guild.model.GuildUpdateModel;
import ru.kifor.kek.guild.service.GuildService;

@RestController
@RequestMapping("/guild")
public class GuildController {

  @Autowired
  private GuildService service;

  @PostMapping("/")
  public GuildModel create(
      @RequestBody GuildCreateModel createModel
  ){
    return service.create(createModel);
  }

  @GetMapping("/{id}")
  public GuildModel getById(
      @PathVariable(name="id") Long id
  ){
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public GuildModel update(
      @PathVariable(name="id") Long id,
      @RequestBody GuildUpdateModel schemaUpdate
  ){
    return service.update(id, schemaUpdate);
  }

  @DeleteMapping("/{id}")
  public void delete(
      @PathVariable(name="id") Long id){
    service.delete(id);
  }

  @GetMapping("/all")
  public BasePageble<GuildModel> getAll(
      @RequestParam(required = false, name = "name") String name,
      @RequestParam(required = false, name = "page", defaultValue = "0") int page,
      @RequestParam(required = false, name = "limit", defaultValue = "10") int limit
  ){
    return service.getAll(
        GuildFilterModel.builder()
            .name(name)
            .page(page)
            .limit(limit)
            .build()
    );
  }

}

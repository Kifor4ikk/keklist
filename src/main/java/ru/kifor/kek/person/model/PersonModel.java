package ru.kifor.kek.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.enums.Spec;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.item.model.ItemModel;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonModel extends BaseModel {
  private String name;
  private int gearScore;
  private Spec spec;
  private Long guildId;
  private GuildModel guild;
  private List<ItemModel> items;
}

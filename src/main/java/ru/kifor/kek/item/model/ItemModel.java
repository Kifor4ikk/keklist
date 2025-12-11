package ru.kifor.kek.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.kifor.kek.base.model.BaseModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemModel extends BaseModel {
  private String name;
}

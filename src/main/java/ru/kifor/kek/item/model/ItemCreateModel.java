package ru.kifor.kek.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kifor.kek.base.model.BaseCreateModel;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemCreateModel extends BaseCreateModel {
  private String name;
}

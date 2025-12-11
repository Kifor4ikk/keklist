package ru.kifor.kek.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ItemToPerson {
  private Long personId;
  private Long itemId;
}

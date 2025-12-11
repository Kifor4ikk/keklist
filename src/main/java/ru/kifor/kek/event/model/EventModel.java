package ru.kifor.kek.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.person.model.PersonModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventModel extends BaseModel {
  private String name;
  private int gold;
  private List<PersonModel> persons;
}

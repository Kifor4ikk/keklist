package ru.kifor.kek.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventToPerson {
  private Long personId;
  private Long eventId;
}

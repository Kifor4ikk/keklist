package ru.kifor.kek.event.service;

import org.springframework.stereotype.Service;
import ru.kifor.kek.base.service.BaseServiceImpl;
import ru.kifor.kek.event.model.EventCreateModel;
import ru.kifor.kek.event.model.EventFilterModel;
import ru.kifor.kek.event.model.EventModel;
import ru.kifor.kek.event.model.EventUpdateModel;
import ru.kifor.kek.event.repository.EventRepository;
import ru.kifor.kek.tables.Event;

@Service
public class EventService
    extends BaseServiceImpl<Event, EventCreateModel, EventModel, EventUpdateModel, EventFilterModel, EventRepository> {
}

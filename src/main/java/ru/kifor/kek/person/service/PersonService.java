package ru.kifor.kek.person.service;

import org.springframework.stereotype.Service;
import ru.kifor.kek.base.service.BaseService;
import ru.kifor.kek.base.service.BaseServiceImpl;
import ru.kifor.kek.person.model.PersonCreateModel;
import ru.kifor.kek.person.model.PersonFilterModel;
import ru.kifor.kek.person.model.PersonModel;
import ru.kifor.kek.person.model.PersonUpdateModel;
import ru.kifor.kek.person.repository.PersonRepository;
import ru.kifor.kek.tables.Person;

@Service
public class PersonService extends BaseServiceImpl<
    Person,
    PersonCreateModel,
    PersonModel,
    PersonUpdateModel,
    PersonFilterModel,
    PersonRepository
    > {
}

package ru.kifor.kek.invite.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kifor.kek.base.model.BaseUpdateModel;
import ru.kifor.kek.base.service.BaseService;
import ru.kifor.kek.base.service.BaseServiceImpl;
import ru.kifor.kek.invite.model.InviteCreateModel;
import ru.kifor.kek.invite.model.InviteFilterModel;
import ru.kifor.kek.invite.model.InviteModel;
import ru.kifor.kek.invite.repository.InviteRepository;
import ru.kifor.kek.tables.Invites;

import java.util.List;

@Service
public class InviteService extends BaseServiceImpl<
    Invites,
    InviteCreateModel,
    InviteModel,
    BaseUpdateModel,
    InviteFilterModel,
    InviteRepository> {
}

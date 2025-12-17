package ru.kifor.kek.guild.service;

import org.springframework.stereotype.Service;
import ru.kifor.kek.base.service.BaseServiceImpl;
import ru.kifor.kek.guild.model.GuildCreateModel;
import ru.kifor.kek.guild.model.GuildFilterModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.guild.model.GuildUpdateModel;
import ru.kifor.kek.guild.repository.GuildRepository;
import ru.kifor.kek.tables.Guild;

@Service
public class GuildService
    extends BaseServiceImpl<Guild, GuildCreateModel, GuildModel, GuildUpdateModel, GuildFilterModel, GuildRepository> {
}

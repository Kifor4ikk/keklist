package ru.kifor.kek.guild.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kifor.kek.base.controller.BaseController;
import ru.kifor.kek.guild.model.GuildCreateModel;
import ru.kifor.kek.guild.model.GuildFilterModel;
import ru.kifor.kek.guild.model.GuildModel;
import ru.kifor.kek.guild.model.GuildUpdateModel;
import ru.kifor.kek.guild.service.GuildService;

@RestController
@RequestMapping("/guild")
public class GuildController extends BaseController<
    GuildCreateModel,
    GuildModel,
    GuildUpdateModel,
    GuildFilterModel,
    GuildService> {
}

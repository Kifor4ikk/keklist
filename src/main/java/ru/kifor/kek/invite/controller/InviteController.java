package ru.kifor.kek.invite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kifor.kek.base.model.BasePageble;
import ru.kifor.kek.invite.model.InviteCreateModel;
import ru.kifor.kek.invite.model.InviteFilterModel;
import ru.kifor.kek.invite.model.InviteModel;
import ru.kifor.kek.invite.service.InviteService;

import java.time.LocalDate;
import java.util.Optional;


@RestController
@RequestMapping("/guild/invite")
public class InviteController{
    @Autowired
    private InviteService inviteService;

    @PostMapping("/create")
    public InviteModel createInvite(@RequestBody InviteCreateModel inviteCreateModel){
        return inviteService.create(inviteCreateModel);
    }

    @GetMapping("/{id}")
    public InviteModel getInvite(@PathVariable("id") Long id){
        return inviteService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteInvite(@PathVariable("id") Long id){
        inviteService.delete(id);
    }

    @GetMapping("/all")
    public BasePageble<InviteModel> getAllInvites(

          @RequestParam(required = false, name = "minDate") Long personId,
          @RequestParam(required = false, name = "maxDate") Long guildId,
          @RequestParam(required = false, name = "maxDate") Boolean result,
          @RequestParam(required = false, name = "maxDate") Boolean isProcessed,

          @RequestParam(required = false, name = "minDate") LocalDate minDate,
          @RequestParam(required = false, name = "maxDate") LocalDate maxDate,

          @RequestParam(required = false, name = "page", defaultValue = "0") int page,
          @RequestParam(required = false, name = "limit", defaultValue = "10") int limit
    ) {
        return this.inviteService.getAll(
            InviteFilterModel.builder()
                .personId(Optional.ofNullable(personId))
                .guildId(Optional.ofNullable(guildId))
                .result(Optional.ofNullable(result))
                .isProcessed(Optional.ofNullable(isProcessed))
                .dateMin(Optional.ofNullable(minDate))
                .dateMax(Optional.ofNullable(maxDate))
                .page(page)
                .limit(limit)
                .build()
        );
      }
}

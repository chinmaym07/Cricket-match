package src.com.cricketgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.com.cricketgame.DTO.RequestDTOs.PlayOverDTO;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.services.InningsService;

@RestController
public class InningsController {
    @Autowired
    private InningsService inningsService;

    @RequestMapping("/matches/{matchId}/innings")
    public Innings getInningDetails(@PathVariable int matchId, @RequestParam String inningsType) {
        if (inningsType.equals("first"))
            return inningsService.getFirstInnings(matchId);
        else
            return inningsService.getSecondInnings(matchId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/innings/start-innings")
    public Innings startInnings(@PathVariable int matchId, @RequestParam String inningsType) {
        if (inningsType.equals("first"))
            return inningsService.startFirstInnings(matchId);
        else
            return inningsService.startSecondInnings(matchId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/innings/play-over")
    public String playOver(@PathVariable int matchId, @RequestParam String inningsType, @RequestBody PlayOverDTO playOverDTO) {

        return inningsService.playOver(matchId, inningsType, playOverDTO.getCurrentBowlerId(), playOverDTO.getCurrentBatsmanId());

    }
}

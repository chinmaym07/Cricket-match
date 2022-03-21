package src.com.cricketgame.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.com.cricketgame.DTO.RequestDTOs.PlayOverDTO;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.services.InningsServiceImpl;

@RestController
public class InningsController {
    @Autowired
    private InningsServiceImpl inningsService;

    @GetMapping("/matches/{matchId}/innings")
    @ApiOperation(value = "Get Innings details for a match")
    public Innings getInningsDetails(@PathVariable int matchId, @RequestParam String inningsType) {
        if (inningsType.equals("first"))
            return inningsService.getFirstInnings(matchId);
        else
            return inningsService.getSecondInnings(matchId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/innings/start-innings")
    @ApiOperation(value = "Start New Innings for a match")
    public Innings startInnings(@PathVariable int matchId, @RequestParam String inningsType) {
        if (inningsType.equals("first"))
            return inningsService.startFirstInnings(matchId);
        else
            return inningsService.startSecondInnings(matchId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/innings/play-over")
    @ApiOperation(value = "Start Bowling for the current Innings of a match")
    public String playOver(@PathVariable int matchId, @RequestParam String inningsType, @RequestBody PlayOverDTO playOverDTO) {

        return inningsService.startOver(matchId, inningsType, playOverDTO.getCurrentBowlerId(), playOverDTO.getCurrentBatsmanId());

    }
}

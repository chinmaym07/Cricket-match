package src.com.cricketgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.com.cricketgame.DTO.RequestDTOs.StartTossDTO;
import src.com.cricketgame.DTO.ResponseDTOs.TossDTO;
import src.com.cricketgame.services.TossService;

@RestController
public class TossController {

    @Autowired
    private TossService tossService;

    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/start-toss")
    public String startToss(@PathVariable int matchId, @RequestBody StartTossDTO startTossDTO) {
        return tossService.startToss(matchId, startTossDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/make-choice")
    public String startToss(@PathVariable int matchId, @RequestBody String winnerChoice) {
        return tossService.makeChoice(matchId, winnerChoice);
    }

    @RequestMapping("/matches/{matchId}/toss-details")
    public TossDTO getTossDetails(@PathVariable int matchId) {

        return tossService.getTossDetails(matchId);
    }
}

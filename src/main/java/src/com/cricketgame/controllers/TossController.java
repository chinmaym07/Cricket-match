package src.com.cricketgame.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.com.cricketgame.DTO.RequestDTOs.StartTossDTO;
import src.com.cricketgame.DTO.ResponseDTOs.TossDTO;
import src.com.cricketgame.services.TossServiceImpl;

@RestController
public class TossController {

    @Autowired
    private TossServiceImpl tossService;

    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/start-toss")
    @ApiOperation(value = "Start toss for a new match")
    public String startToss(@PathVariable int matchId, @RequestBody StartTossDTO startTossDTO) {
        return tossService.startToss(matchId, startTossDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/matches/{matchId}/make-choice")
    @ApiOperation(value = "Make choice between bat or bowl for a new match")
    public String startToss(@PathVariable int matchId, @RequestBody String winnerChoice) {
        return tossService.makeChoice(matchId, winnerChoice);
    }

    @GetMapping("/matches/{matchId}/toss-details")
    @ApiOperation(value = "Get Toss details for a match")
    public TossDTO getTossDetails(@PathVariable int matchId) {

        return tossService.getTossDetails(matchId);
    }
}

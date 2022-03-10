package src.com.cricketgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.com.cricketgame.DTO.RequestDTOs.NewMatchDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchSummaryDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.models.Match;
import src.com.cricketgame.services.MatchService;

import java.util.List;

@RestController
public class MatchController {
    @Autowired
    private MatchService matchService;

    @RequestMapping("/matches")
    public List<MatchesDTO> getAllMatches() {
        return matchService.getAllMatches();
    }
    @RequestMapping("/teams/{teamId}/matches")
    public List<MatchesDTO> getMatchesForATeam(@PathVariable int teamId) {
        return matchService.getMatchesForATeam(teamId);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/matches/start-new-match")
    public MatchesDTO startMatch(@RequestBody NewMatchDTO newMatchDTO) {
        return matchService.startMatch(newMatchDTO);
    }

    @RequestMapping("/matches/{matchId}")
    public MatchesDTO getMatchDetails(@PathVariable int matchId) {
        return matchService.getMatchDetails(matchId);
    }


    @RequestMapping("/matches/{matchId}/match-summary")
    public MatchSummaryDTO getMatchSummary(@PathVariable int matchId) {
        return matchService.getMatchSummary(matchId);
    }



}

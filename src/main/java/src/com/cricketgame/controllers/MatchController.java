package src.com.cricketgame.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.com.cricketgame.DTO.RequestDTOs.NewMatchDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchSummaryDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.services.MatchServiceImpl;

import java.util.List;

@RestController
public class MatchController {
    @Autowired
    private MatchServiceImpl matchService;

    @GetMapping("/matches")
    @ApiOperation(value = "Get all match details")
    public List<MatchesDTO> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/teams/{teamId}/matches")
    @ApiOperation(value = "Get all matches for a team")
    public List<MatchesDTO> getMatchesForATeam(@PathVariable int teamId) {
        return matchService.getMatchesForATeam(teamId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/matches/start-new-match")
    @ApiOperation(value = "Start a new match between two teams")
    public MatchesDTO startMatch(@RequestBody NewMatchDTO newMatchDTO) {
        return matchService.startMatch(newMatchDTO);
    }

    @GetMapping("/matches/{matchId}")
    @ApiOperation(value = "Get Match Details for a particular match")
    public MatchesDTO getMatchDetails(@PathVariable int matchId) {
        return matchService.getMatchDetails(matchId);
    }


    @GetMapping("/matches/{matchId}/match-summary")
    @ApiOperation(value = "Get Match Summary for a particular match")
    public MatchSummaryDTO getMatchSummary(@PathVariable int matchId) {
        return matchService.getMatchSummary(matchId);
    }

    @GetMapping("/matches/{matchId}/team-stats")
    @ApiOperation(value = "Get Team Stats for a particular match")
    public List<PlayerStatsDTO> getTeamStats(@PathVariable int matchId, @RequestParam String teamName) {
        return matchService.getTeamStats(matchId, teamName);
    }

}

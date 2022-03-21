package src.com.cricketgame.controllers;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.services.PlayerServiceImpl;

import java.util.List;

@RestController
public class PlayersController {

    @Autowired
    private PlayerServiceImpl playerService;

    @GetMapping("/teams/{teamId}/players")
    @ApiOperation(value = "Get all players for a team")
    public List<Player> getTeamPlayers(@PathVariable int teamId) {
        return playerService.getTeamPlayers(teamId);
    }

    @GetMapping("/players/{playerId}")
    @ApiOperation(value = "Get Player Info")
    public Player getPlayer(@PathVariable int playerId) {
        return playerService.getPlayer(playerId);
    }

    @GetMapping("/players/{matchId}/player-stats/{playerId}")
    @ApiOperation(value = "Get Player stats for a particular match")
    public PlayerStatsDTO getPlayerStats(@PathVariable int playerId, @PathVariable int matchId) {
        return playerService.getPlayerStats(playerId, matchId);
    }

    /*@RequestMapping("/matches/{matchId}/player-stats")
    public MatchesDTO getMatchDetails(@PathVariable int matchId) {
        return matchService.getMatchDetails(matchId);
    }*/

}

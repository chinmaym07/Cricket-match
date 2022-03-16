package src.com.cricketgame.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.services.PlayerServiceImpl;

import java.util.List;

@RestController
public class PlayersController {

    @Autowired
    private PlayerServiceImpl playerService;

    @RequestMapping("/teams/{teamId}/players")
    public List<Player> getTeamPlayers(@PathVariable int teamId) {
        return playerService.getTeamPlayers(teamId);
    }

    @RequestMapping("/players/{playerId}")
    public Player getPlayer(@PathVariable int playerId) {
        return playerService.getPlayer(playerId);
    }

    @RequestMapping("/players/{matchId}/player-stats/{playerId}")
    public PlayerStatsDTO getPlayerStats(@PathVariable int playerId, @PathVariable int matchId) {
        return playerService.getPlayerStats(playerId, matchId);
    }

    /*@RequestMapping("/matches/{matchId}/player-stats")
    public MatchesDTO getMatchDetails(@PathVariable int matchId) {
        return matchService.getMatchDetails(matchId);
    }*/

}

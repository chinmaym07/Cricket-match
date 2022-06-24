package src.com.cricketgame.services;

import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Player;

import java.util.List;

public interface PlayerService {
    Player getPlayer(int teamId);

    List<Player> getTeamPlayers(int teamId);

    PlayerStatsDTO getPlayerStats(int playerId, int matchId);
}

package src.com.cricketgame.interfaces;

import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.PlayerStats;

import java.util.List;

public interface PlayerRepository {

    List<Player> getTeamPlayers(int teamId);

    Player getPlayer(int playerId);

    PlayerStatsDTO getPlayerStats(int playerId, int matchId);

    void updatePlayerStats(int matchId, PlayerStats playerStats);
    void insertPlayerStats(int matchId, PlayerStats playerStats);

}

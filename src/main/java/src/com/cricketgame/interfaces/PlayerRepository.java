package src.com.cricketgame.interfaces;

import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.PlayerStats;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    List<Player> getTeamPlayers(int teamId);

    Player getPlayer(int playerId);

    PlayerStatsDTO getPlayerStats(int playerId, int matchId);

    void updatePlayerStats(int matchId, PlayerStatsDTO playerStatsDTO);

    void insertPlayerStats(int matchId, PlayerStatsDTO playerStats);

}

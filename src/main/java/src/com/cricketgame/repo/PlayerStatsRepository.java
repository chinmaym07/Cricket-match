package src.com.cricketgame.repo;

import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;

public interface PlayerStatsRepository {

    PlayerStatsDTO getPlayerStats(int playerId, int matchId);

    void updatePlayerStats(int matchId, PlayerStatsDTO playerStatsDTO);

    void insertPlayerStats(int matchId, PlayerStatsDTO playerStats);

    boolean checkForPlayerStats(int matchId, int playerId);
}

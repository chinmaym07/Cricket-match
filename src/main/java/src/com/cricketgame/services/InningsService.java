package src.com.cricketgame.services;

import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.CurrentPlay;
import src.com.cricketgame.models.Innings;

public interface InningsService {
    Innings getFirstInnings(int matchId);

    Innings getSecondInnings(int matchId);

    Innings startFirstInnings(int matchId);

    Innings startSecondInnings(int matchId);

    void findWinner(int matchId, MatchesDTO matchesDTO);

    String startOver(int matchId, String inningsType, int currentBowlerId, int currentBatsmanId);

    String playOver(MatchesDTO matchesDTO, Innings innings, PlayerStatsDTO batsmanStats, PlayerStatsDTO bowlerStats, CurrentPlay currentPlay, boolean secondInnings);
}

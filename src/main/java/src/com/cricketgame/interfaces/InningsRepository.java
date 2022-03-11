package src.com.cricketgame.interfaces;

import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.CurrentPlay;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.WicketsHistory;
import src.com.cricketgame.utils.BallSummary;

import java.util.HashMap;
import java.util.List;

public interface InningsRepository {
    List<Innings> getMatchInnings(int matchId);

    Innings getFirstInnings(int matchId);

    Innings getSecondInnings(int matchId);

    void updateInningsStatus(int matchId, String inningsType);

    void updateInningsStats(int matchId, Innings innings);

    void insertInningsStats(int matchId, Innings innings);

    void insertWicketsHistory(int matchId, int inningsId, WicketsHistory currentWicket);

    void insertBallSummary(int matchId, int inningsId, BallSummary ballSummary);

    void updateCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay);

    void insertCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay);


    boolean checkForPlayerStatsInEachRunFreq(int matchId, int playerId);

    void updateInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsFreq);

    void insertInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsFreq);


    boolean checkCurrentPlayPresent(int matchId, int inningsId);

    Innings startFirstInnings(int matchId);

    Innings startSecondInnings(int matchId);

    CurrentPlay getCurrentPlayForInnings(int matchId, int inningsId);

    String playOver(MatchesDTO matchesDTO, Innings innings, PlayerStatsDTO batsmanStats, PlayerStatsDTO bowlerStats, CurrentPlay currentPlay);

}

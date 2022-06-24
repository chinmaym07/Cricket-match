package src.com.cricketgame.repo;

import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;

public interface EachRunFreqRepository {
    boolean checkForPlayerStatsInEachRunFreq(int matchId, int playerId);

    void updateInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsFreq);

    void insertInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsFreq);


}

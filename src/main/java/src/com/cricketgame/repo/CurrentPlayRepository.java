package src.com.cricketgame.repo;

import src.com.cricketgame.models.CurrentPlay;

public interface CurrentPlayRepository {
    void updateCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay);

    void insertCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay);

    boolean checkCurrentPlayPresent(int matchId, int inningsId);

    CurrentPlay getCurrentPlayForInnings(int matchId, int inningsId);
}

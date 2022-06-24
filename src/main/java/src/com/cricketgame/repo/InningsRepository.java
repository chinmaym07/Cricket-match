package src.com.cricketgame.repo;

import src.com.cricketgame.models.Innings;

import java.util.List;

public interface InningsRepository {

    int getInningsIdCount();

    List<Innings> getMatchInnings(int matchId);

    Innings getFirstInnings(int matchId);

    Innings getSecondInnings(int matchId);

    void updateInningsStatus(int matchId, String inningsType);

    void updateInningsStats(int matchId, Innings innings);

    void insertInningsStats(int matchId, Innings innings);

    Innings startFirstInnings(int matchId);

    Innings startSecondInnings(int matchId);

    void updatePartialInningsDetails(Innings innings);
}

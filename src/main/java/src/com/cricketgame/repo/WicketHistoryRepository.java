package src.com.cricketgame.repo;

import src.com.cricketgame.models.WicketsHistory;

import java.util.List;

public interface WicketHistoryRepository {
    void insertWicketsHistory(int matchId, int inningsId, WicketsHistory currentWicket);

    List<WicketsHistory> getWicketsHistory(int matchId, int inningsId);
}

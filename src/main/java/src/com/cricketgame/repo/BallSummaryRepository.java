package src.com.cricketgame.repo;

import src.com.cricketgame.utils.BallSummary;

import java.util.List;

public interface BallSummaryRepository {
    void insertBallSummary(int matchId, int inningsId, BallSummary ballSummary);

    List<String> getBallSummaryForInnings(int matchId, int inningsId, int currentOver);
}

package src.com.cricketgame.interfaces;

import src.com.cricketgame.models.Innings;

import java.util.List;

public interface InningsRepository {
    List<Innings> getMatchInnings(int matchId);
}

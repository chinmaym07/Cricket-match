package src.com.cricketgame.repo;

import src.com.cricketgame.models.Player;

import java.util.List;

public interface PlayerRepository {

    List<Player> getTeamPlayers(int teamId);

    Player getPlayer(int playerId);
}

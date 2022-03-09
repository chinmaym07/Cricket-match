package src.com.cricketgame.interfaces;

import src.com.cricketgame.models.Team;

import java.util.List;

public interface TeamRepository {
    List<Team> getAllTeams();
    Team getSpecificTeamById(int teamId);
    Team getSpecificTeamByName(String name);
}

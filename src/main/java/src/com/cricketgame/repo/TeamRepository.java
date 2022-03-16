package src.com.cricketgame.repo;

import src.com.cricketgame.models.Team;

import java.util.List;

public interface TeamRepository {
    List<Team> getAllTeams();

    Team getSpecificTeamById(int teamId);

    Team getSpecificTeamByName(String name);

    int getSpecificTeamIdByName(String name);
}

package src.com.cricketgame.services;

import src.com.cricketgame.models.Team;

import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();

    Team getSpecificTeamById(int teamId);

    Team getSpecificTeamByName(String teamName);

    int getSpecificTeamIdByName(String teamName);
}

package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.repo.TeamRepositoryImpl;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepositoryImpl teamRepository;


    public List<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    public Team getSpecificTeamById(int teamId) {
        return teamRepository.getSpecificTeamById(teamId);
    }

    public Team getSpecificTeamByName(String teamName) {
        return teamRepository.getSpecificTeamByName(teamName);
    }

    public int getSpecificTeamIdByName(String teamName) {
        return teamRepository.getSpecificTeamIdByName(teamName);
    }
}

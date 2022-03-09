package src.com.cricketgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.services.TeamService;

import java.sql.SQLException;
import java.util.List;


@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping("/teams")
    public List<Team> getAllTeams() throws SQLException {
        return teamService.getAllTeams();
    }

    @RequestMapping("/create-team")
    public List<Team> createNewTeam() throws SQLException {
        return teamService.getAllTeams();
    }

    @RequestMapping("/teams/{teamId}")
    public Team getSpecificTeamById(@PathVariable int teamId) {
        return teamService.getSpecificTeamById(teamId);
    }

    public Team getSpecificTeamByName(@RequestParam(value="name") String teamName){
        return teamService.getSpecificTeamByName(teamName);
    }
}

package src.com.cricketgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.services.TeamServiceImpl;

import java.sql.SQLException;
import java.util.List;


@RestController
public class TeamController {

    @Autowired
    private TeamServiceImpl teamService;

    @RequestMapping("/teams")
    public List<Team> getAllTeams() throws SQLException {
        return teamService.getAllTeams();
    }

    @RequestMapping("/team")
    public Team getAllTeams(@RequestParam String teamName) throws SQLException {
        return teamService.getSpecificTeamByName(teamName);
    }

    @RequestMapping("/create-team")
    public List<Team> createNewTeam() throws SQLException {
        return teamService.getAllTeams();
    }

    @RequestMapping("/teams/{teamId}")
    public Team getSpecificTeamById(@PathVariable int teamId) {
        return teamService.getSpecificTeamById(teamId);
    }


    @RequestMapping("/specific-team")
    public int getSpecificTeamIdByName(@RequestParam(value = "name") String teamName) {
        return teamService.getSpecificTeamIdByName(teamName);
    }
}

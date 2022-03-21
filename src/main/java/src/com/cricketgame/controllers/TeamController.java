package src.com.cricketgame.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/teams")
    @ApiOperation(value = "Get all teams")
    public List<Team> getAllTeams() throws SQLException {
        return teamService.getAllTeams();
    }

    @GetMapping("/team")
    @ApiOperation(value = "Get particular team by teamName")
    public Team getAllTeams(@RequestParam String teamName) throws SQLException {
        return teamService.getSpecificTeamByName(teamName);
    }

    /*@GetMapping("/create-team")
    public List<Team> createNewTeam() throws SQLException {
        return teamService.getAllTeams();
    }*/

    @GetMapping("/teams/{teamId}")
    @ApiOperation(value = "Get particular team by teamId")
    public Team getSpecificTeamById(@PathVariable int teamId) {
        return teamService.getSpecificTeamById(teamId);
    }


    @GetMapping("/specific-team")
    @ApiOperation(value = "Get particular team by teamName")
    public int getSpecificTeamIdByName(@RequestParam(value = "name") String teamName) {
        return teamService.getSpecificTeamIdByName(teamName);
    }
}

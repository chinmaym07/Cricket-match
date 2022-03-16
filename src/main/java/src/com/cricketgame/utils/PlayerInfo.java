package src.com.cricketgame.utils;

import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.PlayerStats;

import java.util.ArrayList;

public class PlayerInfo {
    public static void displayPlayerInfo(ArrayList<Player> players) {
        System.out.println("PlayerId \t\t PlayerName \t\t\t\t Player Role");
        for (Player currentPlayer : players) {
            System.out.println(currentPlayer.getPlayerId() + " \t\t\t\t " + currentPlayer.getName() + " \t\t\t\t" + currentPlayer.getRole());
        }
    }
    public static void displayPlayerStats(PlayerStatsDTO playerStatsDTO)
    {
        System.out.println("Player Id = "+ playerStatsDTO.getPlayerId());
        System.out.println("Player Name = "+ playerStatsDTO.getName());
        System.out.println("Role = "+ playerStatsDTO.getRole());
        System.out.println("Team Name = "+ playerStatsDTO.getTeamName());
        System.out.println("Runs Scored = "+ playerStatsDTO.getRunsScored());
        System.out.println("Runs Given = "+ playerStatsDTO.getRunsGiven());
        System.out.println("Wickets Taken = "+ playerStatsDTO.getWicketsTaken());
        System.out.println("Balls Faced = "+ playerStatsDTO.getBallsFaced());
        System.out.println("Balls Bowled = "+ playerStatsDTO.getBallsBowled());
        System.out.println("No of Wide Balls = "+ playerStatsDTO.getNoOfWideBalls());
        System.out.println("No of NO Balls = "+ playerStatsDTO.getNoOfNoBalls());
        System.out.println("Average Strike Rate = "+ playerStatsDTO.getAverageStrikeRate());
        System.out.println("Economy = "+ playerStatsDTO.getEconomy());
        System.out.println("Overs Bowled "+ playerStatsDTO.getOversBowled());

        System.out.println("Playing status = "+ playerStatsDTO.getPlayingStatus());



    }

}

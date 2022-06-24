package src.com.cricketgame.utils;

import src.com.cricketgame.models.Innings;

public class InningsUtils {
    public static void inningsStats(Innings innings) {
        System.out.println("Innings Id "+ innings.getInningsId());
        System.out.println("Batting team Id: "+ innings.getBattingTeamId());
        System.out.println("Bowling team Id: "+ innings.getBowlingTeamId());
        System.out.println("Total Score "+ innings.getTotalScore());
        System.out.println("Overs batted: "+ innings.getOversBatted());
        System.out.println("Wickets Fallen: "+ innings.getWicketsFallen());
        System.out.println("Extra Runs: "+ innings.getExtraRuns());
        System.out.println("No of Wide Balls: "+ innings.getNoOfWideBalls());
        System.out.println("No of No Balls: "+ innings.getNoOfNoBalls());
    }
}

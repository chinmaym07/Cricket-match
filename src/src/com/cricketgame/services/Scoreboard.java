package src.com.cricketgame.services;

import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.WicketsHistory;

import java.util.ArrayList;

public class Scoreboard {
    public static void printBattingScorecard(ArrayList<Player> playerArr, Innings innings) {
        int playersDidNotBat = 0;
        System.out.println("Batter \t\t\t\t Runs Score \t\t\t\t Ball's Faced \t\t\t\t 4's \t\t\t\t 6's \t\t\t\t StrikeRate\n");
        for (Player currentPlayer : playerArr) {
            // System.out.println(currentPlayer.getName() + " played "+currentPlayer.getBallsFaced() +" balls.");
            if (currentPlayer.getBallsFaced() > 0)
                // System.out.printf(currentPlayer.getName() + " scored " + currentPlayer.getRunsScored() + " in " + currentPlayer.getBallsFaced() + " balls at a strike rate of %4.1f\n", currentPlayer.getAverageStrikeRate());
                System.out.printf(currentPlayer.getName() + "\t\t\t\t\t\t" + currentPlayer.getRunsScored() + "\t\t\t\t\t\t\t" + currentPlayer.getBallsFaced() + "\t\t\t\t\t\t  " + currentPlayer.getNumberOfRunsFreq(4) + "\t\t\t\t\t  " + currentPlayer.getNumberOfRunsFreq(6) + "\t\t\t\t\t   %4.1f\n", currentPlayer.getAverageStrikeRate());
            else playersDidNotBat++;
        }
        System.out.println("Extras \t\t\t\t\t" + innings.getExtraRuns() + "(nb " + innings.getNoOfNoBalls() + ", wb " + innings.getNoOfWideBalls() + ")");
        System.out.println("Total \t\t\t\t\t" + innings.getTotalScore() + "(" + innings.getFallOfWickets() + " wckts, " + innings.getOversBatted() + " Ov.)");
        if (playersDidNotBat > 0) {
            System.out.println("Did not Bat");
            int count = 0; // checking the count as this will be used for printing comma
            for (Player currentPlayer : playerArr) {
                if (currentPlayer.getBallsFaced() == 0) {
                    System.out.print(currentPlayer.getName());
                    count++;
                    if (count != playersDidNotBat) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void printWicketsHistory(ArrayList<WicketsHistory> wicketsHistory) {
        int count = 0;
        System.out.println("Fall of Wickets");
        for (WicketsHistory wickets : wicketsHistory) {
            System.out.printf(wickets.getRunScored() + "-" + wickets.getWicketsDown() + " (" + wickets.getBatsmanName() + ", %4.1f)", wickets.getWickerFallenInOver());
            count++;
            if (count != wicketsHistory.size()) System.out.printf(", ");
        }
        System.out.println("\n");
    }

    public static void printBowlingStats(ArrayList<Player> players) {
        System.out.println("Bowler \t\t Over's Bowled \t\t Maiden Overs \t\t Runs Given \t\t  Wicket's Taken \t\t NoBall's \t\t WideBall's \t\t Economy\n");
        for (Player currentPlayer : players) {
            // System.out.println(currentPlayer.getName() + " played "+currentPlayer.getBallsBowled() +" balls.");

            if (currentPlayer.getBallsBowled() > 0)
                System.out.printf(currentPlayer.getName() + "\t\t\t\t %4.1f" + "\t\t\t\t" + currentPlayer.getMaidenOvers() + "\t\t\t\t\t" + currentPlayer.getRunsGiven() + "\t\t\t\t\t" + currentPlayer.getWicketsTaken() + "\t\t\t\t\t\t" + currentPlayer.getNoOfNoBalls() + "\t\t\t\t" + currentPlayer.getNoOfWideBalls() + "\t\t\t\t   %4.1f\n", currentPlayer.getOversBowled(), currentPlayer.getEconomy());
        }
    }

}

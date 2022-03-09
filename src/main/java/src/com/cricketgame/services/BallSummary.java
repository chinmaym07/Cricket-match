package src.com.cricketgame.services;

import java.util.ArrayList;

public class BallSummary {
    public static void printBallSummary(ArrayList<ArrayList<String>> ballSummary) {
        System.out.println("Current Over \t\tOutcome on Balls");
        for (int currentOver = 0; currentOver < ballSummary.size(); currentOver++) {
            System.out.print(currentOver + "\t\t\t\t\t");
            for (String outcome : ballSummary.get(currentOver)) {
                System.out.print(outcome + " ");
            }
            System.out.println();
        }
    }
}

package src.com.cricketgame.test;

import src.com.cricketgame.services.MatchController;

public class MatchTest {
    public static void main(String[] args) {

        String nameA = "Lucknow Super Giants"; // TeamA name
        String nameB = "Mumbai Indians"; // TeamB Name
        int matchOvers = 50; // setting match overs

        MatchController match = new MatchController(); // creating instance of Match Controller clss

        match.createTwoTeams(nameA, nameB, matchOvers); // Creating two teams

        match.playMatch(); // Start Playing the match
    }
}

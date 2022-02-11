package src.com.cricketgame.test;

import src.com.cricketgame.models.MatchController;

public class MatchTest {
    public static void main(String[] args) {
        MatchController match = new MatchController(); // creating instance of Match Controller clss
        String nameA = "Lucknow Super Giants"; // TeamA name
        String nameB = "Mumbai Indians"; // TeamB Name
        match.setMatchOvers(50); // setting match overs
        match.createTwoTeams(nameA,nameB); // Creating two teams
        match.playMatch(); // Start Playing the match
    }
}

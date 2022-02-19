package src.com.cricketgame.test;

import src.com.cricketgame.models.Toss;
import src.com.cricketgame.services.MatchController;

import java.util.Scanner;

public class MatchTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String nameA, nameB;
        System.out.println("Enter TeamA Name: ");
        nameA = sc.nextLine(); // TeamA name
        System.out.println("Enter TeamB Name: ");
        nameB = sc.nextLine(); // TeamB Name
        System.out.println("Enter Match overs: ");
        int matchOvers = sc.nextInt(); // setting match overs

        MatchController matchController = new MatchController(); // creating instance of Match Controller clss

        matchController.createTwoTeams(nameA, nameB, matchOvers); // Creating two teams

        matchController.playMatch(); // Start Playing the match
    }
}

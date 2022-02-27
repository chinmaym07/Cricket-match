package src.com.cricketgame.test;

import src.com.cricketgame.models.Toss;
import src.com.cricketgame.services.MatchController;

import java.util.Scanner;

public class MatchTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String nameA="", nameB="";
        int matchOvers=0;
        System.out.println("Enter TeamA Name: ");

        try {
            nameA = sc.nextLine(); // TeamA name
            if(nameA.length() <= 0)
                throw new Exception("Please Enter a valid Team Name !!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Enter TeamB Name: ");
        try {
            nameB = sc.nextLine(); // TeamB Name
            if(nameB.length() <= 0) {
                throw new Exception("Please Enter a valid Team Name !!");
            }
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Enter Match overs: ");
        try {
            matchOvers = sc.nextInt(); // setting match overs
            if(matchOvers <= 0)
                throw new Exception("Match Overs cannot be less than 0");
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        MatchController matchController = new MatchController(); // creating instance of Match Controller clss

        matchController.createTwoTeams(nameA, nameB, matchOvers); // Creating two teams

        matchController.playMatch(); // Start Playing the match
    }
}

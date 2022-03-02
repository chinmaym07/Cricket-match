package src.com.cricketgame.models;

import java.util.Scanner;

public class Toss {

    private String onTossOutcome, winnerChoseTo, callerChoice;
    private int tossWinner, whoWillBat, whoWillBowl, whoTookTheCall;

    public int getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(int tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getWinnerChoseTo() {
        return winnerChoseTo;
    }

    public void setWinnerChoseTo(String winnerChoseTo) {
        this.winnerChoseTo = winnerChoseTo;
    }

    public String getCallerChoice() {
        return callerChoice;
    }

    public void setCallerChoice(String callerChoice) {
        this.callerChoice = callerChoice;
    }

    public String getOnTossOutcome() {
        return onTossOutcome;
    }

    public void setOnTossOutcome(String onTossOutcome) {
        this.onTossOutcome = onTossOutcome;
    }

    public int getWhoTookTheCall() {
        return whoTookTheCall;
    }

    public void setWhoTookTheCall(int whoTookTheCall) {
        this.whoTookTheCall = whoTookTheCall;
    }

    public int getWhoWillBowl() {
        return whoWillBowl;
    }

    public void setWhoWillBowl(int getWhoWillBowl) {
        this.whoWillBowl = getWhoWillBowl;
    }

    public int getWhoWillBat() {
        return whoWillBat;
    }

    public void setWhoWillBat(int whoWillBat) {
        this.whoWillBat = whoWillBat;
    }

    private void decideWhoWillBatOrBowl(int selectFirst, int teamAId, int teamBId) {
        if (selectFirst == 0) {
            setWinnerChoseTo("Bat");
            if (tossWinner == teamAId) {
                setWhoWillBat(teamAId);
                setWhoWillBowl(teamBId);
            } else {
                setWhoWillBat(teamBId);
                setWhoWillBowl(teamAId);
            }
        } else {
            setWinnerChoseTo("Bowl");
            if (tossWinner == teamAId) {
                setWhoWillBat(teamBId);
                setWhoWillBowl(teamAId);
            } else {
                setWhoWillBat(teamAId);
                setWhoWillBowl(teamBId);
            }
        }
    }

    private void tossStatusMessage(Team teamA, Team teamB, String callingTeam, int choice, int outComeOnToss) {
        boolean outcomeEqualToChoice = choice == outComeOnToss;
        if (outcomeEqualToChoice) {
            if (callingTeam.equals(teamA.getTeamName())) //
                setTossWinner(teamA.getTeamId());
            else
                setTossWinner(teamB.getTeamId());
            if (choice == 0)
                setOnTossOutcome("Head");
            else
                setOnTossOutcome("Tail");

            if (callingTeam.equals(teamA.getTeamName())) {
                if (choice == 0)
                    System.out.println("So Head's is the call & it is Head, " + teamA.getTeamName() + " have won the toss");
                else
                    System.out.println("So Tail's is the call & it is Tail, " + teamA.getTeamName() + " have won the toss");
            } else {
                if (choice == 0)
                    System.out.println("So Head's is the call & it's Head, " + teamB.getTeamName() + " have won the toss");
                else
                    System.out.println("So Tail's is the call & it's Tail, " + teamB.getTeamName() + " have won the toss");
            }
        } else {
            if (callingTeam.equals(teamA.getTeamName()))
                setTossWinner(teamB.getTeamId());
            else
                setTossWinner(teamA.getTeamId());
            if (outComeOnToss == 0)
                setOnTossOutcome("Head");
            else
                setOnTossOutcome("Tail");

            if (callingTeam.equals(teamA.getTeamName())) {
                if (outComeOnToss == 0)
                    System.out.println("So Tail's is the call & it's Head, " + teamB.getTeamName() + " have won the toss");
                else
                    System.out.println("So Head's is the call & it's Tail, " + teamB.getTeamName() + " have won the toss");
            } else {
                if (outComeOnToss == 0)
                    System.out.println("So Tail's is the call & it's Head, "+ teamA.getTeamName() + " have won the toss");
                else
                    System.out.println("So Head's is the call & it's Tail, "+ teamA.getTeamName() + " have won the toss");
            }
        }
    }

    private void tossUtil(String callingTeam, Team teamA, Team teamB) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice: Head or Tail");
        System.out.println("Enter 0 for HEAD");
        System.out.println("Enter 1 for TAIL");
        int choice = sc.nextInt();

        setCallerChoice(choice == 0 ? "Head" : "Tail");
        int outComeOnToss = (int) (Math.random() * 2);
        // I have considered 0 for head & 1 for tail
        System.out.println("Toss Outcome: " + outComeOnToss);
        tossStatusMessage(teamA, teamB, callingTeam, choice, outComeOnToss);

        System.out.println("What will you choose ?");
        System.out.println("Enter 0 for Batting");
        System.out.println("Enter 1 for Bowling");
        int selectFirst = sc.nextInt();
        decideWhoWillBatOrBowl(selectFirst, teamA.getTeamId(), teamB.getTeamId());
    }

    public void tossSetup(Team teamA, Team teamB) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Now will play the coin for the Toss");
        System.out.println("Please Decide who will take the call & enter 1 for "+teamA.getTeamName()+" or 2 for "+teamB.getTeamName()+" whoever will take the call");
        int call = sc.nextInt();
        // Here TeamA is considered as 0 & TeamB is considered as 1

        if (call == 1) {
            setWhoTookTheCall(teamA.getTeamId());
            tossUtil(teamA.getTeamName(), teamA, teamB);
        } else {
            setWhoTookTheCall(teamB.getTeamId());
            tossUtil(teamB.getTeamName(), teamA, teamB);
        }
    }
}

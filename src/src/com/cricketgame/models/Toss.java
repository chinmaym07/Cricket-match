package src.com.cricketgame.models;

import java.util.Scanner;

public class Toss {
    private String onTossOutcome, winnerChoseTo, whoTookTheCall;
    private int callerChoice,tossWinner, whoWillBat, whoWillBowl;

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

    public void setCallerChoice(int callerChoice) {
        this.callerChoice = callerChoice;
    }

    public void setWhoTookTheCall(String whoTookTheCall) {
        this.whoTookTheCall = whoTookTheCall;
    }

    public int getCallerChoice() {
        return callerChoice;
    }

    public String getOnTossOutcome() {
        return onTossOutcome;
    }

    public String getWhoTookTheCall() {
        return whoTookTheCall;
    }

    public void setOnTossOutcome(String onTossOutcome) {
        this.onTossOutcome = onTossOutcome;
    }

    public void setWhoWillBowl(int getWhoWillBowl) {
        this.whoWillBowl = getWhoWillBowl;
    }

    public void setWhoWillBat(int whoWillBat) {
        this.whoWillBat = whoWillBat;
    }

    public int getWhoWillBowl() {
        return whoWillBowl;
    }

    public int getWhoWillBat() {
        return whoWillBat;
    }
    private void decideWhoWillBatOrBowl(int selectFirst) {
        if (selectFirst == 0) {
            setWinnerChoseTo("Bat");
            if(tossWinner == 0)
            {
                setWhoWillBat(0);
                setWhoWillBowl(1);
            } else
            {
                setWhoWillBat(1);
                setWhoWillBowl(0);
            }
        } else {
            setWinnerChoseTo("Bowl");
            if(tossWinner == 0)
            {
                setWhoWillBat(1);
                setWhoWillBowl(0);
            } else
            {
                setWhoWillBat(0);
                setWhoWillBowl(1);
            }
        }
    }

    private void tossUtil(String callingTeam) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice: Head or Tail");
        System.out.println("Enter 0 for HEAD");
        System.out.println("Enter 1 for TAIL");
        int choice = sc.nextInt();
        int outComeOnToss = (int) (Math.random() * 2);
        // I have considered 0 for head & 1 for tail
        System.out.println("Toss Outcome: "+outComeOnToss);
        if (choice == outComeOnToss) { // this is when choice & random select are same then
            if (callingTeam.equals("Team A")) //
                setTossWinner(0);
            else
                setTossWinner(1);
            if (choice == 0)
                setOnTossOutcome("HEAD");
            else
                setOnTossOutcome("TAIL");
            if (callingTeam.equals("Team A"))
            {
                if(choice == 0)
                    System.out.println("So Head's is the call & it is Head, Team A have won the toss");
                else
                    System.out.println("So Tail's is the call & it is Tail, Team A have won the toss");
            }
            else
            {
                if(choice == 0)
                    System.out.println("So Head's is the call & it's Head, Team B have won the toss");
                else
                    System.out.println("So Tail's is the call & it's Tail, Team B have won the toss");
            }

            System.out.println("What will you choose ?");
            System.out.println("Enter 0 for Batting");
            System.out.println("Enter 1 for Bowling");
            int selectFirst = sc.nextInt();
            decideWhoWillBatOrBowl(selectFirst);
        } else {
            if (callingTeam.equals("Team A"))
                setTossWinner(1);
            else
                setTossWinner(0);
            if (outComeOnToss == 0)
                setOnTossOutcome("HEAD");
            else
                setOnTossOutcome("TAIL");

            if (callingTeam.equals("Team A"))
            {
                if(outComeOnToss == 0)
                    System.out.println("So Tail's is the call & it's Head, Team B have won the toss");
                else
                    System.out.println("So Head's is the call & it's Tail, Team B have won the toss");
            }
            else
            {
                if(outComeOnToss == 0)
                    System.out.println("So Tail's is the call & it's Head, Team A have won the toss");
                else
                    System.out.println("So Head's is the call & it's Tail, Team A have won the toss");
            }


            System.out.println("What will you choose ?");
            System.out.println("Enter 0 for Batting");
            System.out.println("Enter 1 for Bowling");
            int selectFirst = sc.nextInt();
            decideWhoWillBatOrBowl(selectFirst);
        }
    }

    public void tossSetup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Now will play the coin for the Toss");
        System.out.println("Please Decide who will take the call & enter 1 or 2 for whoever will take the call");
        int call = sc.nextInt();
        // Here TeamA is considered as 0 & TeamB is considered as 1
        if (call == 1) {
            tossUtil("Team A");
        } else {
            tossUtil("Team B");
        }
    }
}

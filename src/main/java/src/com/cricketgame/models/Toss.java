package src.com.cricketgame.models;

public class Toss {

    private String tossOutcome, winnerChoseTo, callersChoice;
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

    public String getCallersChoice() {
        return callersChoice;
    }

    public void setCallersChoice(String callersChoice) {
        this.callersChoice = callersChoice;
    }

    public String getTossOutcome() {
        return tossOutcome;
    }

    public void setTossOutcome(String tossOutcome) {
        this.tossOutcome = tossOutcome;
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

    private String tossStatusMessage(Team teamA, Team teamB, String callingTeam, String choice, String tossOutcome) {
        boolean outcomeEqualToChoice = choice.equals(tossOutcome);
        String resp = "";
        if (outcomeEqualToChoice) {
            if (callingTeam.equals(teamA.getTeamName())) //
                setTossWinner(teamA.getTeamId());
            else
                setTossWinner(teamB.getTeamId());
            setTossOutcome(choice);

            if (callingTeam.equals(teamA.getTeamName())) {
                if (choice.equals("Head"))
                    resp = "So Head's is the call & it is Head, " + teamA.getTeamName() + " have won the toss";
                else
                    resp = "So Tail's is the call & it is Tail, " + teamA.getTeamName() + " have won the toss";
            } else {
                if (choice.equals("Head"))
                    resp = "So Head's is the call & it's Head, " + teamB.getTeamName() + " have won the toss";
                else
                    resp = "So Tail's is the call & it's Tail, " + teamB.getTeamName() + " have won the toss";
            }
        } else {
            if (callingTeam.equals(teamA.getTeamName()))
                setTossWinner(teamB.getTeamId());
            else
                setTossWinner(teamA.getTeamId());

            setTossOutcome(tossOutcome);

            if (callingTeam.equals(teamA.getTeamName())) {
                if (tossOutcome.equals("Head"))
                    resp = "So Tail's is the call & it's Head, " + teamB.getTeamName() + " have won the toss";
                else
                    resp = "So Head's is the call & it's Tail, " + teamB.getTeamName() + " have won the toss";
            } else {
                if (tossOutcome.equals("Head"))
                    resp = "So Tail's is the call & it's Head, "+ teamA.getTeamName() + " have won the toss";
                else
                    resp = "So Head's is the call & it's Tail, "+ teamA.getTeamName() + " have won the toss";
            }
        }
        resp += "\nNow Make a choice between Bat or Bowl";
        return resp;
    }


    public String tossUtil(String callingTeam, Team teamA, Team teamB, String choice) {

        setCallersChoice(choice.toLowerCase().equals("head") ? "Head" : "Tail");
        int outComeOnToss = (int) (Math.random() * 2);
        String tossOutcome = outComeOnToss == 0 ? "Head" : "Tail";

        return tossStatusMessage(teamA, teamB, callingTeam, choice, tossOutcome);

    }


    /*public void tossUtil(String callingTeam, Team teamA, Team teamB) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice: Head or Tail");
        System.out.println("Enter 0 for HEAD");
        System.out.println("Enter 1 for TAIL");
        int choice = sc.nextInt();

        setCallersChoice(choice == 0 ? "Head" : "Tail");
        int outComeOnToss = (int) (Math.random() * 2);
        // I have considered 0 for head & 1 for tail
        System.out.println("Toss Outcome: " + outComeOnToss);
        tossStatusMessage(teamA, teamB, callingTeam, choice, outComeOnToss);

        System.out.println("What will you choose ?");
        System.out.println("Enter 0 for Batting");
        System.out.println("Enter 1 for Bowling");
        int selectFirst = sc.nextInt();
        decideWhoWillBatOrBowl(selectFirst, teamA.getTeamId(), teamB.getTeamId());
    }*/

    /*public void tossSetup(Team teamA, Team teamB) {
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
    }*/
}

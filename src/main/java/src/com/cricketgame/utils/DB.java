package src.com.cricketgame.utils;

import src.com.cricketgame.enums.MatchWinnerEnums;
import src.com.cricketgame.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DB {
    private static String DB_URL = "jdbc:mysql://localhost:3306/cricketmatch";
    private static String USERNAME = "root";
    private static String PASSWORD = "root1234";
    private static Connection connection;

    public DB() throws ClassNotFoundException {
        setupConnection();
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setupConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Successfully connected to DB !!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }


    public static int getMatchIdCount() throws SQLException {
        try {
            Statement st = connection.createStatement();
            String sql = "Select count(matchId) from matches";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return 0;
    }

    public static int getInningsIdCount() throws SQLException {
        try {
            Statement st = connection.createStatement();
            String sql = "Select count(inningsId) from innings";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return 0;
    }

    public static void createTeam(Team team, String teamName) throws ClassNotFoundException, SQLException {
        try {
            ResultSet rs1;
            Statement st = connection.createStatement();
            String stmt1 = "select * from team t where t.teamName='" + teamName + "'";
            rs1 = st.executeQuery(stmt1);
            rs1.next();
            int teamId = rs1.getInt(1);
            team.setTeamId(teamId);
            String captainName = rs1.getString(3);
            String stmt2 = "select * from player p inner join teamplayer t on t.playerId=p.playerId where teamId=" + teamId;
            rs1 = st.executeQuery(stmt2);
            List<Player> players = team.getPlayersArr();
            while (rs1.next()) {
                int playerId = rs1.getInt(1);
                String playerName = rs1.getString(2);
                String playerRole = rs1.getString(3);
                Player teamPlayer = new Player(playerId, playerName, playerRole);
                if (playerName.equals(captainName)) team.setCaptainName(playerName);
                players.add(teamPlayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void updateTossDetails(Toss tossObj, int matchId) throws SQLException {
        try {
            Statement st = connection.createStatement();
            String sql = "insert into toss (matchId, teamIdWhoWonTheToss, teamIdWhoTookTheCall, teamIdWhoWillBat, teamIdWhoWillBowl, tossOutcome, callersChoice) values (" + matchId + "," + tossObj.getTeamIdWhoWonTheToss() + "," + tossObj.getTeamIdWhoTookTheCall() + "," + tossObj.getTeamIdWhoWillBat() + "," + tossObj.getTeamIdWhoWillBowl() + ",'" + tossObj.getTossOutcome() + "','" + tossObj.getCallersChoice() + "')";
            int count = st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void storeMatchData(Match match) throws SQLException {
        try {
            Statement st = connection.createStatement();
            int teamIdWhoWonTheToss = match.getTeamNameWhoWonToss().equals(match.getTeamA().getTeamName()) ? match.getTeamA().getTeamId() : match.getTeamB().getTeamId();
            String sql = "insert into matches (matchId, teamAId, teamBId, teamIdWhoWontheToss, matchOvers) values (" + match.getMatchId() + "," + match.getTeamA().getTeamId() + "," + match.getTeamB().getTeamId() + "," + teamIdWhoWonTheToss + "," + match.getMatchOvers() + ")";
            int count = st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void updateMatchData(Match match) throws SQLException {
        try {
            Statement st = connection.createStatement();
            int teamIdWhoWonMatch = match.getMatchWinner().equals(MatchWinnerEnums.TEAMA) ? match.getTeamA().getTeamId() : match.getTeamB().getTeamId();
            String sql = "Update matches Set teamABestPlayerId = " + match.getTeamABestPlayer().getPlayerId() + ", teamBBestPlayerId = " + match.getTeamBBestPlayer().getPlayerId() + ", teamIdWhoWonTheMatch = " + teamIdWhoWonMatch + " where matchId = " + match.getMatchId();
            System.out.println(sql);
            int count = st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void updatePlayerStats(ArrayList<Player> players, int matchId) throws SQLException {
        try {
            int count = 0;
            for (Player currentPlayer : players) {
                PlayerStats currentPlayerStats = currentPlayer.getPlayerStats();
                Statement st = connection.createStatement();
                String sql = "Insert into playerstats (matchId, playerId, runsScored, runsGiven, wicketsTaken, ballsFaced, noOfNoBalls, noOfWideBalls,maidenOvers,ballsBowled,oversBowled) values (" + matchId + "," + currentPlayer.getPlayerId() + "," + currentPlayerStats.getRunsScored() + "," + currentPlayerStats.getRunsGiven() + "," + currentPlayerStats.getWicketsTaken() + "," + currentPlayerStats.getBallsFaced() + "," + currentPlayerStats.getNoOfNoBalls() + "," + currentPlayerStats.getNoOfWideBalls() + "," + currentPlayerStats.getMaidenOvers() + "," + currentPlayerStats.getBallsBowled() + "," + currentPlayerStats.getOversBowled() + ")";
                count += st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void updateInningsStats(int matchId, Innings innings) throws SQLException {
        try {
            int count = 0;

            Statement st = connection.createStatement();
            String sql = "Insert into innings (inningsId, matchId, battingTeamId, bowlingTeamId, totalScore, wicketsFallen, extraRuns, noOfNoBalls, noOfWideBalls, oversBatted) values (" + innings.getInningsId() + "," + matchId + "," + innings.getBattingTeamId() + "," + innings.getBowlingTeamId() + "," + innings.getTotalScore() + "," + innings.getWicketsFallen() + "," + innings.getExtraRuns() + "," + innings.getNoOfNoBalls() + "," + innings.getNoOfWideBalls() + "," + innings.getOversBatted() + ")";
            count = st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void updateWicketsHistory(int matchId, int inningsId, ArrayList<WicketsHistory> wicketsHistories) throws SQLException {
        try {
            int count = 0;
            for (WicketsHistory currentWicket : wicketsHistories) {
                Statement st = connection.createStatement();
                String sql = "Insert into wicketshistory (inningsId,matchId, batsmanId, bowlerId, wicketsFallenInOver, runScored, wicketsDown) values (" + inningsId + "," + matchId + "," + currentWicket.getBatsmanId() + "," + currentWicket.getBowlerId() + "," + currentWicket.getWicketsFallenInOver() + "," + currentWicket.getRunScored() + "," + currentWicket.getWicketsDown() + ")";
                count += st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void createNewTeam() {

    }

    public static void addNewPlayers() {

    }
}

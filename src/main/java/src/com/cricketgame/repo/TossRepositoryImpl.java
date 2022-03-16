package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.RequestDTOs.StartTossDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;

@Repository
public class TossRepositoryImpl implements TossRepository {
    Toss toss;
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<Toss> rowMapper = new BeanPropertyRowMapper<>(Toss.class);

    public void updateTossDetails(Toss toss, int matchId) {
        String sql = "insert into `Toss` (matchId, teamIdWhoWonTheToss, teamIdWhoTookTheCall, teamIdWhoWillBat, teamIdWhoWillBowl, tossOutcome, callersChoice) values (?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, matchId, toss.getTeamIdWhoWonTheToss(), toss.getTeamIdWhoTookTheCall(), toss.getTeamIdWhoWillBat(), toss.getTeamIdWhoWillBowl(), toss.getTossOutcome(), toss.getCallersChoice());
        if (status != 0)
            System.out.println("Toss data Updated");
        else
            System.out.println("Toss data not inserted");
    }


    public Toss getTossDetails(int matchId) {
        String sql = "Select * from `Toss` where matchId = ?";
        Toss toss = jdbcTemplate.queryForObject(sql, rowMapper, matchId);
        return toss;
    }

    public String startToss(Team teamA, Team teamB, MatchesDTO matchesDTO, StartTossDTO startTossDTO) {
        toss = new Toss();
        String tossUpdate;
        if (startTossDTO.getCallerName().equals(teamA.getTeamName())) {
            toss.setTeamIdWhoTookTheCall(teamA.getTeamId());
            tossUpdate = toss.tossUtil(teamA.getTeamName(), teamA, teamB, startTossDTO.getChoice());
        } else {
            toss.setTeamIdWhoTookTheCall(teamB.getTeamId());
            tossUpdate = toss.tossUtil(teamB.getTeamName(), teamA, teamB, startTossDTO.getChoice());
        }


        if (toss.getTeamIdWhoWonTheToss() == teamA.getTeamId()) matchesDTO.setTeamWhoWonToss(teamA.getTeamName());
        else matchesDTO.setTeamWhoWonToss(teamB.getTeamName());
        updatePartialTossDetails(toss, matchesDTO.getMatchId());
        return tossUpdate;
    }

    public void updateTossDetailsInMatchTable(Toss toss, int matchId) {
        String sql1 = "SET foreign_key_checks = 0";
        String sql2 = "SET foreign_key_checks = 1";
        jdbcTemplate.execute(sql1);
        String sql = "Update `Matches` set teamIdWhoWonTheToss = ? where matchId = ?";
        int status = jdbcTemplate.update(sql, toss.getTeamIdWhoWonTheToss(), matchId);
        if (status != 0)
            System.out.println("Updated Toss details in match");
        else
            System.out.println("No Toss details Updated");
        jdbcTemplate.execute(sql2);

    }

    public void updatePartialTossDetails(Toss toss, int matchId) {
        String sql1 = "SET foreign_key_checks = 0";
        String sql2 = "SET foreign_key_checks = 1";
        jdbcTemplate.execute(sql1);
        String sql = "Insert into `Toss` (matchId, teamIdWhoWonTheToss, teamIdWhoTookTheCall, tossOutcome, callersChoice) values (?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, matchId, toss.getTeamIdWhoWonTheToss(), toss.getTeamIdWhoTookTheCall(), toss.getTossOutcome(), toss.getCallersChoice());
        if (status != 0)
            System.out.println("Updated Partial toss details");
        else
            System.out.println("No Partial toss Update occurred");
        jdbcTemplate.execute(sql2);
    }

    public void updateCompleteTossDetails(Toss toss, int matchId) {
        String sql1 = "SET foreign_key_checks = 0";
        String sql2 = "SET foreign_key_checks = 1";
        jdbcTemplate.execute(sql1);
        String sql = "Update `Toss` t set teamIdWhoWillBat = ? , teamIdWhoWillBowl = ? where matchId = ?";
        int status = jdbcTemplate.update(sql, toss.getTeamIdWhoWillBat(), toss.getTeamIdWhoWillBowl(), matchId);
        if (status != 0)
            System.out.println("Updated Complete toss details");
        else
            System.out.println("No Complete toss Update occurred");
        jdbcTemplate.execute(sql2);
    }

    public String makeChoice(int matchId, String winnerChoice, MatchesDTO matchesDTO, Innings firstInnings, Innings secondInnings) {


        Toss toss = getTossDetails(matchId);
        int teamAId = matchesDTO.getTeamAId();
        int teamBId = matchesDTO.getTeamBId();
        toss.setWinnerChoseTo(winnerChoice);
        if (winnerChoice.equals("Bat")) {
            if (toss.getTeamIdWhoWonTheToss() == teamAId) {
                firstInnings.setBattingTeamId(teamAId);
                firstInnings.setBowlingTeamId(teamBId);
                secondInnings.setBattingTeamId(teamBId);
                secondInnings.setBowlingTeamId(teamAId);
            } else {
                firstInnings.setBattingTeamId(teamBId);
                firstInnings.setBowlingTeamId(teamAId);
                secondInnings.setBattingTeamId(teamAId);
                secondInnings.setBowlingTeamId(teamBId);
            }
        } else {
            if (toss.getTeamIdWhoWonTheToss() == teamAId) {
                firstInnings.setBattingTeamId(teamBId);
                firstInnings.setBowlingTeamId(teamAId);
                secondInnings.setBattingTeamId(teamAId);
                secondInnings.setBowlingTeamId(teamBId);
            } else {
                firstInnings.setBattingTeamId(teamAId);
                firstInnings.setBowlingTeamId(teamBId);
                secondInnings.setBattingTeamId(teamBId);
                secondInnings.setBowlingTeamId(teamAId);
            }
        }
        if (firstInnings.getBattingTeamId() == teamAId) {
            toss.setTeamIdWhoWillBat(teamAId);
            toss.setTeamIdWhoWillBowl(teamBId);
        } else {
            toss.setTeamIdWhoWillBat(teamBId);
            toss.setTeamIdWhoWillBowl(teamAId);
        }
        firstInnings.setMatchId(matchesDTO.getMatchId());
        matchesDTO.setFirstInnings(firstInnings);
        secondInnings.setMatchId(matchesDTO.getMatchId());
        matchesDTO.setSecondInnings(secondInnings);
        updateCompleteTossDetails(toss, matchId);
        updateTossDetailsInMatchTable(toss, matchId);
        return "Toss Completed";
    }
}

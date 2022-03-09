package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.RequestDTOs.StartTossDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.interfaces.TossRepository;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;

@Repository
public class TossRepositoryImpl implements TossRepository {
    Toss toss;
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<Toss> rowMapper = new BeanPropertyRowMapper<>(Toss.class);


    public int getInningsIdCount() {
        String sql = "Select count(inningsId) from innings";
        return jdbcTemplate.queryForObject(sql, Integer.class);
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
            toss.setWhoTookTheCall(teamA.getTeamId());
            tossUpdate = toss.tossUtil(teamA.getTeamName(), teamA, teamB, startTossDTO.getChoice());
        } else {
            toss.setWhoTookTheCall(teamB.getTeamId());
            tossUpdate = toss.tossUtil(teamB.getTeamName(), teamA, teamB, startTossDTO.getChoice());
        }


        if (toss.getTossWinner() == teamA.getTeamId()) matchesDTO.setTeamWhoWonToss(teamA.getTeamName());
        else matchesDTO.setTeamWhoWonToss(teamB.getTeamName());
        updatePartialTossDetails(toss, matchesDTO.getMatchId());
        return tossUpdate;
    }

    public void updatePartialTossDetails(Toss toss, int matchId) {
        String sql = "Insert into `Toss` (matchId, teamIdWhoWonTheToss, teamIdWhoTookTheCall, tossOutcome, callersChoice) values (?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, matchId, toss.getTossWinner(), toss.getWhoTookTheCall(), toss.getTossOutcome(), toss.getCallersChoice());
        if(status != 0)
            System.out.println("Updated Partial toss details");
        else
            System.out.println("No Update occured");
    }

    public void updateCompleteTossDetails(Toss toss, int matchId) {
        String sql = "Update `Toss` t set teamIdWhoWillBat = ? , teamIdWhoWillBowl = ? where matchId = ?";
        int status = jdbcTemplate.update(sql, toss.getWhoWillBat(),toss.getWhoWillBowl(),matchId);
        if(status != 0)
            System.out.println("Updated Complete toss details");
        else
            System.out.println("No Update occured");
    }

    public String makeChoice(int matchId, String winnerChoice, MatchesDTO matchesDTO) {
        Innings firstInnings = new Innings();
        firstInnings.setInningsId(getInningsIdCount() + 1);
        Innings secondInnings = new Innings();
        secondInnings.setInningsId(getInningsIdCount() + 2);

        Toss toss= getTossDetails(matchId);
        int teamAId = matchesDTO.getTeamAId();
        int teamBId = matchesDTO.getTeamBId();

        if(winnerChoice.equals("Bat")) {
            if(toss.getTossWinner() == teamAId)
            {
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
        } else
        {
            if(toss.getTossWinner() == teamAId) {
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

        firstInnings.setMatchId(matchesDTO.getMatchId());
        /*match.setFirstInnings(firstInnings);
        secondInnings.setMatchId(match.getMatchId());
        match.setSecondInnings(secondInnings);
        */

        return "Toss Completed";
    }
}

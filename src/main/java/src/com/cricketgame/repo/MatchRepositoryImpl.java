package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.ResponseDTOs.InningsDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchSummaryDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.TossDTO;
import src.com.cricketgame.enums.MatchStatus;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Match;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;

import java.util.List;

@Repository
public class MatchRepositoryImpl implements MatchRepository {

    private Match match;
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public final RowMapper<MatchesDTO> matchesDTORowMapper = new BeanPropertyRowMapper<>(MatchesDTO.class);

    public int getMatchIdCount() {
        String sql = "Select count(matchId) from `Matches`";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


    public List<MatchesDTO> getAllMatches() {
        String sql = "Select * from `Matches`";
        return jdbcTemplate.query(sql, matchesDTORowMapper);
    }

    public List<MatchesDTO> getMatchesForATeam(int teamId) {
        String sql = "Select * from `Matches` m where m.teamAId = ? or m.teamBId = ? ";
        return jdbcTemplate.query(sql, matchesDTORowMapper, teamId, teamId);
    }

    public MatchesDTO getMatchDetails(int matchId) {
        String sql = "Select * from `Matches` m where m.matchId= ? ";
        return jdbcTemplate.queryForObject(sql, matchesDTORowMapper, matchId);
    }

    public MatchSummaryDTO getMatchSummary(MatchesDTO matchesDTO, Innings firstInnings, Innings secondInnings, Toss toss) {

        int teamAId = matchesDTO.getTeamAId();
        int teamBId = matchesDTO.getTeamBId();
        String teamAName = matchesDTO.getTeamAName();
        String teamBName = matchesDTO.getTeamBName();

        InningsDTO firstInningsDTO = new InningsDTO();
        firstInningsDTO.convertInningsToInningsDTO(firstInnings, teamAId, teamBId, teamAName, teamBName);

        InningsDTO secondInningsDTO = new InningsDTO();
        secondInningsDTO.convertInningsToInningsDTO(secondInnings, teamAId, teamBId, teamAName, teamBName);

        TossDTO tossDTO = new TossDTO();

        tossDTO.convertTossToTossDTO(toss, teamAId, teamBId, teamAName, teamBName);

        MatchSummaryDTO matchSummaryDTO = new MatchSummaryDTO();
        matchSummaryDTO.setMatchesDTO(matchesDTO);
        matchSummaryDTO.setFirstInningsDTO(firstInningsDTO);
        matchSummaryDTO.setSecondInningsDTO(secondInningsDTO);
        matchSummaryDTO.setTossDTO(tossDTO);

        return matchSummaryDTO;
    }


    public MatchesDTO startNewMatch(Team teamA, Team teamB, int matchOvers) {
        match = new Match(getMatchIdCount());

        match.setTeamA(teamA); // set Team A
        match.setTeamB(teamB); // set Team B

        match.setMatchOvers(matchOvers); // setting match overs
        match.setMatchStatus(MatchStatus.ONGOING);
        storeMatchData(match);
        MatchesDTO matchesDTO = getMatchDetails(match.getMatchId());
        matchesDTO.setTeamAName(teamA.getTeamName());
        matchesDTO.setTeamBName(teamB.getTeamName());
        matchesDTO.setMatchStatus(String.valueOf(MatchStatus.ONGOING));
        return matchesDTO;
    }


    public void storeMatchData(Match match) {
        String sql1 = "SET foreign_key_checks = 0";
        String sql2 = "SET foreign_key_checks = 1";
        jdbcTemplate.execute(sql1);
        String sql = "insert into `Matches` (matchId, teamAId, teamBId, matchOvers) values (?,?,?,?)";
        int status = jdbcTemplate.update(sql, match.getMatchId(), match.getTeamA().getTeamId(), match.getTeamB().getTeamId(), match.getMatchOvers());
        if (status != 0)
            System.out.println("Match data Updated");
        else
            System.out.println("Match data not inserted");
        jdbcTemplate.execute(sql2);
    }

    public void updateMatchDetails(MatchesDTO matchesDTO) {
        int teamIdWhoWonMatch = matchesDTO.getTeamIdWhoWonTheMatch() == matchesDTO.getTeamAId() ? matchesDTO.getTeamAId() : matchesDTO.getTeamBId();
        String sql = "Update matches Set teamIdWhoWonTheMatch = ?, matchStatus = ? where matchId = ? ";
        int status = jdbcTemplate.update(sql, teamIdWhoWonMatch, "COMPLETED", matchesDTO.getMatchId());
        if (status != 0)
            System.out.println("Updated Match Details");
        else
            System.out.println("No Match Details Update occured!");
    }


    /*public ArrayList<Match> getMatchesDetailsForATeam(int teamId) {
        connection = DB.getConnection();
        ArrayList<Match> matches = new ArrayList<Match>();
        try {
            Statement st = connection.createStatement();
            String sql = " select * from matches m inner where m.teamAId = "+teamId +" or m.teamBId = "+ teamId;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                Match currMatch = new Match();
                currMatch.setMatchId(rs.getInt(1));
                currMatch.setTeamNameWhoWonToss();
                currMatch.setMatchOvers(rs.getInt(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


}

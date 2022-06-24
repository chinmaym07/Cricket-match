package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
public class EachRunFreqRepositoryImpl implements EachRunFreqRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<EachRunfreqDTO> eachRunfreqDTORowMapper = new BeanPropertyRowMapper<EachRunfreqDTO>() {
        public EachRunfreqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            EachRunfreqDTO eachRunfreqDTO = new EachRunfreqDTO();
            eachRunfreqDTO.setMatchId(rs.getInt("matchId"));
            eachRunfreqDTO.setPlayerId(rs.getInt("playerId"));
            HashMap<Integer, Integer> runsFreq = new HashMap<Integer, Integer>();
            runsFreq.put(1, rs.getInt("ones"));
            runsFreq.put(2, rs.getInt("twos"));
            runsFreq.put(3, rs.getInt("threes"));
            runsFreq.put(4, rs.getInt("fours"));
            runsFreq.put(5, rs.getInt("fives"));
            runsFreq.put(6, rs.getInt("sixes"));
            eachRunfreqDTO.setEachRunFreq(runsFreq);
            return eachRunfreqDTO;
        }
    };

    public EachRunfreqDTO getPlayerStatsInEachRunFreq(int matchId, int playerId) {
        EachRunfreqDTO eachRunfreqDTO = null;
        try {
            String sql = "Select * from `Eachrunfreq` where matchId= ? and playerId = ? ";
            eachRunfreqDTO = jdbcTemplate.queryForObject(sql, eachRunfreqDTORowMapper, matchId, playerId);
        } catch (EmptyResultDataAccessException e) {

        }
        return eachRunfreqDTO;
    }

    public boolean checkForPlayerStatsInEachRunFreq(int matchId, int playerId) {
        Integer result = null;
        try {
            String sql = "Select * from `Eachrunfreq` where matchId= ? and playerId = ? ";
            result = jdbcTemplate.queryForObject(sql, Integer.class, matchId, playerId);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return result != null && result > 0;
    }

    public void insertInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsFreq) {
        String sql = "Insert into `Eachrunfreq` (matchId,playerId,ones,twos,threes,fours,fives,sixes) values (?,?,?,?,?,?,?,?)";

        int status = jdbcTemplate.update(sql, matchId, playerId, runsFreq.getRunFreq(1), runsFreq.getRunFreq(2), runsFreq.getRunFreq(3), runsFreq.getRunFreq(4), runsFreq.getRunFreq(5), runsFreq.getRunFreq(6));
        if (status != 0)
            System.out.println("Runs freq inserted");
        else
            System.out.println("No Runs freq insert Occurred !!");
    }


    public void updateInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsFreq) {
        String sql = "Update `Eachrunfreq` set ones = ?, twos = ?, threes = ?, fours = ?, fives = ?, sixes = ? where matchId = ? and playerId = ? ";
        int status = jdbcTemplate.update(sql, runsFreq.getRunFreq(1), runsFreq.getRunFreq(2), runsFreq.getRunFreq(3), runsFreq.getRunFreq(4), runsFreq.getRunFreq(5), runsFreq.getRunFreq(6), matchId, playerId);
        if (status != 0)
            System.out.println("Runs freq updated");
        else
            System.out.println("No Runs Freq update Occurred !!");
    }
}

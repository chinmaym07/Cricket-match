package src.com.cricketgame.services;

import src.com.cricketgame.DTO.RequestDTOs.NewMatchDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchSummaryDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;

import java.util.List;

public interface MatchService {
    List<MatchesDTO> getAllMatches();

    List<MatchesDTO> getMatchesForATeam(int teamId);

    List<MatchesDTO> updateMatchesDTOList(List<MatchesDTO> matchesDTOList);

    MatchesDTO getMatchDetails(int matchId);

    MatchesDTO startMatch(NewMatchDTO newMatchDTO);

    MatchSummaryDTO getMatchSummary(int matchId);

    List<PlayerStatsDTO> getTeamStats(int matchId, String teamName);
}

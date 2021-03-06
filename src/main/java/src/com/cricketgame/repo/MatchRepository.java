package src.com.cricketgame.repo;

import src.com.cricketgame.DTO.ResponseDTOs.MatchSummaryDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Match;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;

import java.util.List;

public interface MatchRepository {
    List<MatchesDTO> getAllMatches();

    List<MatchesDTO> getMatchesForATeam(int teamId);

    int getMatchIdCount();

    MatchesDTO startNewMatch(Team teamA, Team teamB, int matchOvers);

    void storeMatchData(Match match);

    void updateMatchDetails(MatchesDTO matchesDTO);

    MatchSummaryDTO getMatchSummary(MatchesDTO matchesDTO, Innings innings1, Innings innings2, Toss toss);
}

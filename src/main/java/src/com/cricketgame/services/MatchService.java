package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.DTO.RequestDTOs.NewMatchDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchSummaryDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;
import src.com.cricketgame.repo.*;

import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepositoryImpl matchRepository;
    @Autowired
    private TeamRepositoryImpl teamRepository;
    @Autowired
    private PlayerRepositoryImpl playerRepository;

    @Autowired
    private TossRepositoryImpl tossRepository;

    @Autowired
    private InningsRepositoryImpl inningsRepository;

    public List<MatchesDTO> getAllMatches() {
        List<MatchesDTO> matchesDTOList = matchRepository.getAllMatches();
        List<MatchesDTO> updateMatchesDTOList = updateMatchesDTOList(matchesDTOList);

        return matchesDTOList;
    }


    public List<MatchesDTO> getMatchesForATeam(int teamId) {
        List<MatchesDTO> matchesDTOList = matchRepository.getMatchesForATeam(teamId);

        List<MatchesDTO> updateMatchesDTOList = updateMatchesDTOList(matchesDTOList);

        return updateMatchesDTOList;
    }

    public List<MatchesDTO> updateMatchesDTOList(List<MatchesDTO> matchesDTOList) {
        for (MatchesDTO matchesDTO : matchesDTOList) {
            Team teamA = teamRepository.getSpecificTeamById(matchesDTO.getTeamAId());
            Team teamB = teamRepository.getSpecificTeamById(matchesDTO.getTeamBId());
            matchesDTO.setTeamAName(teamA.getTeamName());
            matchesDTO.setTeamBName(teamB.getTeamName());
            if (matchesDTO.getTeamIdWhoWonTheToss() == teamA.getTeamId())
                matchesDTO.setTeamWhoWonToss(teamA.getTeamName());
            else
                matchesDTO.setTeamWhoWonToss(teamB.getTeamName());
            if (matchesDTO.getTeamIdWhoWonTheMatch() == teamA.getTeamId())
                matchesDTO.setMatchResult(teamA.getTeamName() + " won the match");
            else if (matchesDTO.getTeamIdWhoWonTheMatch() == teamB.getTeamId())
                matchesDTO.setMatchResult(teamB.getTeamName() + " won the match");
        }
        return matchesDTOList;
    }

    public MatchesDTO getMatchDetails(int matchId) {
        MatchesDTO matchesDTO = matchRepository.getMatchDetails(matchId);
        Team teamA = teamRepository.getSpecificTeamById(matchesDTO.getTeamAId());
        Team teamB = teamRepository.getSpecificTeamById(matchesDTO.getTeamBId());
        matchesDTO.setTeamAName(teamA.getTeamName());
        matchesDTO.setTeamBName(teamB.getTeamName());

        if (matchesDTO.getTeamIdWhoWonTheToss() == teamA.getTeamId())
            matchesDTO.setTeamWhoWonToss(teamA.getTeamName());
        else if (matchesDTO.getTeamIdWhoWonTheToss() == teamB.getTeamId())
            matchesDTO.setTeamWhoWonToss(teamB.getTeamName());

        if (matchesDTO.getTeamIdWhoWonTheMatch() == teamA.getTeamId())
            matchesDTO.setMatchResult(teamA.getTeamName() + " won the match");
        else if (matchesDTO.getTeamIdWhoWonTheMatch() == teamB.getTeamId())
            matchesDTO.setMatchResult(teamB.getTeamName() + " won the match");
        return matchesDTO;
    }

    public MatchesDTO startMatch(NewMatchDTO newMatchDTO) {
        String nameA = newMatchDTO.getTeamAName();
        Team teamA = teamRepository.getSpecificTeamByName(nameA);
        teamA.setPlayersArr(playerRepository.getTeamPlayers(teamA.getTeamId()));
        String nameB = newMatchDTO.getTeamBName();
        Team teamB = teamRepository.getSpecificTeamByName(nameB);
        teamB.setPlayersArr(playerRepository.getTeamPlayers(teamB.getTeamId()));
        return matchRepository.startNewMatch(teamA, teamB, newMatchDTO.getMatchOvers());
    }

    public MatchSummaryDTO getMatchSummary(int matchId) {
        MatchesDTO matchesDTO = getMatchDetails(matchId);
        Toss toss = tossRepository.getTossDetails(matchId);
        Innings firstInnings = inningsRepository.getMatchInnings(matchId).get(0);
        Innings secondInnings = inningsRepository.getMatchInnings(matchId).get(1);

        MatchSummaryDTO matchSummaryDTO = matchRepository.getMatchSummary(matchesDTO, firstInnings, secondInnings, toss);
        return matchSummaryDTO;
    }
}

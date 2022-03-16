package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.DTO.RequestDTOs.NewMatchDTO;
import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchSummaryDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;
import src.com.cricketgame.repo.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
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

    @Autowired
    private PlayerStatsRepositoryImpl playerStatsRepository;

    @Autowired
    private EachRunFreqRepositoryImpl eachRunFreqRepository;

    public List<MatchesDTO> getAllMatches() {
        List<MatchesDTO> matchesDTOList = matchRepository.getAllMatches();
        List<MatchesDTO> updatedMatchesDTOList = updateMatchesDTOList(matchesDTOList);
        return updatedMatchesDTOList;
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

    public List<PlayerStatsDTO> getTeamStats(int matchId, String teamName) {
        MatchesDTO matchesDTO = getMatchDetails(matchId);
        int teamIdWhoseStatsToFetch;
        if (matchesDTO.getTeamAName() == teamName)
            teamIdWhoseStatsToFetch = matchesDTO.getTeamAId();
        else
            teamIdWhoseStatsToFetch = matchesDTO.getTeamBId();
        List<Player> playersList = playerRepository.getTeamPlayers(teamIdWhoseStatsToFetch);
        List<PlayerStatsDTO> playerStatsDTOList = new ArrayList<>();
        for (Player player : playersList) {
            PlayerStatsDTO currentPlayerStats = playerStatsRepository.getPlayerStats(player.getPlayerId(), matchId);
            EachRunfreqDTO eachRunfreqDTO = eachRunFreqRepository.getPlayerStatsInEachRunFreq(matchId, player.getPlayerId());
            if (eachRunfreqDTO != null)
                currentPlayerStats.setEachRunFreq(eachRunfreqDTO);
            if (currentPlayerStats == null) {
                currentPlayerStats = new PlayerStatsDTO();
                currentPlayerStats.setPlayerId(player.getPlayerId());
                currentPlayerStats.setName(player.getName());
                currentPlayerStats.setRole(player.getRole());
                currentPlayerStats.setTeamName(teamName);
                currentPlayerStats.setPlayingStatus("DID_NOT_BAT");
            }

            playerStatsDTOList.add(currentPlayerStats);
        }
        return playerStatsDTOList;
    }
}

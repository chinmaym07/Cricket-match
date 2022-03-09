package src.com.cricketgame.DTO.ResponseDTOs;

import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Toss;

public class MatchSummaryDTO {
    private MatchesDTO matchesDTO;
    private TossDTO tossDTO;
    private InningsDTO firstInningsDTO, secondInningsDTO;

    public TossDTO getTossDTO() {
        return tossDTO;
    }

    public MatchesDTO getMatchesDTO() {
        return matchesDTO;
    }

    public void setMatchesDTO(MatchesDTO matchesDTO) {
        this.matchesDTO = matchesDTO;
    }

    public void setTossDTO(TossDTO tossDTO) {
        this.tossDTO = tossDTO;
    }

    public InningsDTO getSecondInningsDTO() {
        return secondInningsDTO;
    }

    public InningsDTO getFirstInningsDTO() {
        return firstInningsDTO;
    }

    public void setSecondInningsDTO(InningsDTO secondInningsDTO) {
        this.secondInningsDTO = secondInningsDTO;
    }

    public void setFirstInningsDTO(InningsDTO firstInningsDTO) {
        this.firstInningsDTO = firstInningsDTO;
    }
}

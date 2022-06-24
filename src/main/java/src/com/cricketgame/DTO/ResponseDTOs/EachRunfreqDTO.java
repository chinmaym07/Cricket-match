package src.com.cricketgame.DTO.ResponseDTOs;

import java.util.HashMap;

public class EachRunfreqDTO {
    private int playerId;
    private int matchId;
    private HashMap<Integer, Integer> eachRunFreq = new HashMap<Integer, Integer>();

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public HashMap<Integer, Integer> getEachRunFreq() {
        return eachRunFreq;
    }

    public void setEachRunFreq(HashMap<Integer, Integer> eachRunFreq) {
        this.eachRunFreq = eachRunFreq;
    }

    public int getRunFreq(int run) {
        if (eachRunFreq.containsKey(run))
            return eachRunFreq.get(run);
        else
            return 0;

    }

    public void increaseRunFreq(int run) {
        if (eachRunFreq.containsKey(run))
            eachRunFreq.put(run, eachRunFreq.get(run) + 1);
        else
            eachRunFreq.put(run, 1);
    }
}

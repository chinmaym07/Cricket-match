package src.com.cricketgame.DTO.RequestDTOs;

public class PlayOverDTO {
    private int currentBowlerId, currentBatsmanId;

    public int getCurrentBatsmanId() {
        return currentBatsmanId;
    }

    public int getCurrentBowlerId() {
        return currentBowlerId;
    }

    public void setCurrentBatsmanId(int currentBatsmanId) {
        this.currentBatsmanId = currentBatsmanId;
    }

    public void setCurrentBowlerId(int currentBowlerId) {
        this.currentBowlerId = currentBowlerId;
    }
}

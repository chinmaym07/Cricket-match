package src.com.cricketgame.models;

public class WicketsHistory {
    private String bowlerName, batsmanName;
    private double wickerFallenInOver = 0.0;
    private int runScored = 0, wicketsDown = 0, bowlerId, batsmanId;

    public void setWickerFallenInOver(double wickerFallenInOver) {
        this.wickerFallenInOver = wickerFallenInOver;
    }

    public double getWickerFallenInOver() {
        return wickerFallenInOver;
    }

    public int getRunScored() {
        return runScored;
    }

    public void setRunScored(int runScored) {
        this.runScored = runScored;
    }

    public int getWicketsDown() {
        return wicketsDown;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }

    public void setBatsmanName(String batsmanName) {
        this.batsmanName = batsmanName;
    }

    public int getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(int batsmanId) {
        this.batsmanId = batsmanId;
    }

    public void setBowlerId(int bowlerId) {
        this.bowlerId = bowlerId;
    }

    public int getBowlerId() {
        return bowlerId;
    }

    public String getBowlerName() {
        return bowlerName;
    }

    public String getBatsmanName() {
        return batsmanName;
    }

    public void setWicketsDown(int wicketsDown) {
        this.wicketsDown = wicketsDown;
    }
}

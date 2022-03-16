package src.com.cricketgame.models;

public class WicketsHistory {
    private int batsmanId;
    private int bowlerId;
    private String bowlerName;
    private String batsmanName;
    private double wicketsFallenInOver = 0.0;
    private int runScored = 0;
    private int wicketsDown = 0;

    public double getWicketsFallenInOver() {
        return wicketsFallenInOver;
    }

    public void setWicketsFallenInOver(double wicketsFallenInOver) {
        this.wicketsFallenInOver = wicketsFallenInOver;
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

    public void setWicketsDown(int wicketsDown) {
        this.wicketsDown = wicketsDown;
    }

    public int getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(int batsmanId) {
        this.batsmanId = batsmanId;
    }

    public int getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(int bowlerId) {
        this.bowlerId = bowlerId;
    }

    public String getBowlerName() {
        return bowlerName;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }

    public String getBatsmanName() {
        return batsmanName;
    }

    public void setBatsmanName(String batsmanName) {
        this.batsmanName = batsmanName;
    }
}

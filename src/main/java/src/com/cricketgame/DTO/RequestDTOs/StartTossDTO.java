package src.com.cricketgame.DTO.RequestDTOs;

public class StartTossDTO {
    private String callerName, choice;

    public String getCallerName() {
        return callerName;
    }

    public String getChoice() {
        return choice;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}

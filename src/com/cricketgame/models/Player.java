package com.cricketgame.models;

public class Player {
    private static int p = 1;
    private String name, phoneNumber, email, gender, dob, playerType;
    private int playerId;

    Player(String n, String phno, String em, String gn, String db, String ptype) {
        playerId = p;
        name = n;
        email = em;
        phoneNumber = phno;
        gender = gn;
        dob = db;
        playerType = ptype;
        p++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }
}

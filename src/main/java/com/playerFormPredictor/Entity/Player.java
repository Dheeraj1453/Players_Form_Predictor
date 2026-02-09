package com.playerFormPredictor.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private long id;

    //Basic player details
    @Column(nullable = false)
    private String name;

    private String role;
    private String country;

    //Bating stats
    private int matches;
    private int runs;
    private double battingAverage;
    private int highestScore;
    private double strikeRate;

    //Bowling stats
    private int wickets;
    private double economy;
    private double bowlingAverage;

    //Last 5 match data
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> lastFiveScores;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> lastFiveWickets;

    //Form Result
    private String formStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public double getBattingAverage() {
        return battingAverage;
    }

    public void setBattingAverage(double battingAverage) {
        this.battingAverage = battingAverage;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public double getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(double strikeRate) {
        this.strikeRate = strikeRate;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public double getEconomy() {
        return economy;
    }

    public void setEconomy(double economy) {
        this.economy = economy;
    }

    public double getBowlingAverage() {
        return bowlingAverage;
    }

    public void setBowlingAverage(double bowlingAverage) {
        this.bowlingAverage = bowlingAverage;
    }

    public List<Integer> getLastFiveScores() {
        return lastFiveScores;
    }

    public void setLastFiveScores(List<Integer> lastFiveScores) {
        this.lastFiveScores = lastFiveScores;
    }

    public List<Integer> getLastFiveWickets() {
        return lastFiveWickets;
    }

    public void setLastFiveWickets(List<Integer> lastFiveWickets) {
        this.lastFiveWickets = lastFiveWickets;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }
}

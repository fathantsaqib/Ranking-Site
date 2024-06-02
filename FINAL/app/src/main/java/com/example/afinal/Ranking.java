package com.example.afinal;
public class Ranking {
    private int id;
    private String rowName;
    private int ranking;
    private double points;  // Ubah dari int menjadi double
    private Team team;
    private Country country;
    private boolean isFavorite;

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public double getPoints() {  // Ubah dari int menjadi double
        return points;
    }

    public void setPoints(double points) {  // Ubah dari int menjadi double
        this.points = points;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

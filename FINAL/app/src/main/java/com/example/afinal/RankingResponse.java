package com.example.afinal;

import java.util.List;


public class RankingResponse {
    private List<Ranking> rankingList;

    public List<Ranking> getRank() {
        return rankingList;
    }

    public void setRank(List<Ranking> rank) {
        this.rankingList = rank;
    }
}
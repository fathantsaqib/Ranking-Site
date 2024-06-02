package com.example.afinal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {
    @Headers({
            "X-RapidAPI-Key: ae8acb962fmshb29a21eca282c1fp1b3e95jsn4909b2f7ce53",
            "X-RapidAPI-Host: footapi7.p.rapidapi.com"
    })
    @GET("api/rankings/uefa/countries")
    Call<RankResponse> getUEFACountriesRankings();

    @Headers({
            "X-RapidAPI-Key: ae8acb962fmshb29a21eca282c1fp1b3e95jsn4909b2f7ce53",
            "X-RapidAPI-Host: footapi7.p.rapidapi.com"
    })
    @GET("api/rankings/uefa/clubs")
    Call<RankResponse> getUEFAClubsRankings();

}

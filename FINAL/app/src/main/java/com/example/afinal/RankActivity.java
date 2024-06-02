package com.example.afinal;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankActivity extends AppCompatActivity {

    private static final String TAG = "RankActivity";
    private RecyclerView recyclerView;
    private RankAdapter rankAdapter;
    private ProgressBar progressBar;
    private View contentView;
    private Toolbar toolbarCountry;
    private Toolbar toolbarClub;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private List<Rank> originalCountryRankList = new ArrayList<>();
    private List<Rank> originalClubRankList = new ArrayList<>();
    private List<Rank> currentDisplayedList = new ArrayList<>();

    private boolean showingCountries = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        toolbarCountry = findViewById(R.id.toolbar_country);
        toolbarClub = findViewById(R.id.toolbar_club);
        progressBar = findViewById(R.id.progressBar);


        // Get the intent to determine which toolbar to show
        Intent intent = getIntent();
        String dataType = intent.getStringExtra("dataType");

        if ("club".equals(dataType)) {
            setToolbar(toolbarClub);
        } else {
            setToolbar(toolbarCountry);
        }

    }

    private void setToolbar(Toolbar toolbar) {
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Mengatur visibilitas ProgressBar menjadi GONE setelah 2 detik
//                progressBar.setVisibility(View.GONE);
//            }
//        }, 2000);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankAdapter = new RankAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(rankAdapter);


        Intent intent = getIntent();
        String dataType = intent.getStringExtra("dataType");
        if ("club".equals(dataType)) {
            showingCountries = false;
        }

        // Panggil API dan dapatkan data ranking
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<RankResponse> countriesCall = apiService.getUEFACountriesRankings();
        Call<RankResponse> clubsCall = apiService.getUEFAClubsRankings();

        // Panggil API negara
        countriesCall.enqueue(new Callback<RankResponse>() {
            @Override
            public void onResponse(Call<RankResponse> call, Response<RankResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Rank> rankings = response.body().getRanking();
                    originalCountryRankList.addAll(rankings);
                    if (showingCountries) {
                        updateDisplayedList();
                    }
                } else {
                    Log.e(TAG, "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RankResponse> call, Throwable t) {
                Log.e(TAG, "Request error: " + t.getMessage());
            }
        });

        // Panggil API klub
        clubsCall.enqueue(new Callback<RankResponse>() {
            @Override
            public void onResponse(Call<RankResponse> call, Response<RankResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Rank> rankings = response.body().getRanking();
                    originalClubRankList.addAll(rankings);
                    if (!showingCountries) {
                        updateDisplayedList();
                    }
                } else {
                    Log.e(TAG, "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RankResponse> call, Throwable t) {
                Log.e(TAG, "Request error: " + t.getMessage());
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SearchView searchView = findViewById(R.id.search_user);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Hapus callback pencarian sebelumnya (jika ada) sebelum membuat yang baru
                handler.removeCallbacks(searchRunnable);

                // Buat callback baru untuk pencarian dengan penundaan 1.5 detik
                searchRunnable = () -> filter(newText);

                // Jalankan pencarian setelah penundaan 1.5 detik
                handler.postDelayed(searchRunnable, 2000);

                if (!Objects.equals(newText, "")) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                recyclerView.setVisibility(View.GONE);

                return true;
            }
        });
    }


    private void updateDisplayedList() {
        currentDisplayedList.clear();
        if (showingCountries) {
            currentDisplayedList.addAll(originalCountryRankList);
        } else {
            currentDisplayedList.addAll(originalClubRankList);
        }
        rankAdapter.setRanks(new ArrayList<>(currentDisplayedList));
    }

    private void filter(String query) {
        if (query.isEmpty()) {
            // Jika teks kosong, sembunyikan RecyclerView dan reset data adapter
            recyclerView.setVisibility(View.GONE);
            rankAdapter.setRanks(new ArrayList<>());
            progressBar.setVisibility(View.VISIBLE); // Sembunyikan ProgressBar
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        List<Rank> filteredList = new ArrayList<>();
        for (Rank rank : currentDisplayedList) {
            if (rank.getRowName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(rank);
            }
        }
        progressBar.setVisibility(View.GONE); // Sembunyikan ProgressBar

        if (filteredList.isEmpty()) {
            Toast.makeText(RankActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
        }
        recyclerView.setVisibility(View.VISIBLE);
        rankAdapter.setRanks(filteredList);
    }
}

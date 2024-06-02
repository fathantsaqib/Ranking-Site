package com.example.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.fragment.FavoriteFragment;
import com.google.gson.Gson;
import com.example.afinal.Ranking;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private List<Ranking> rankingList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private ExecutorService executorService;

    public RankingAdapter(Context context, List<Ranking> rankings) {
        this.context = context;
        this.rankingList = rankings;
        this.sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.gson = new Gson();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);
        holder.bind(ranking);

        boolean isFavorite = checkIfFavorite(ranking.getId());
        ranking.setFavorite(isFavorite);

        int favoriteIcon = ranking.isFavorite() ? R.drawable.favoritefilled : R.drawable.favorite;
        holder.favoriteButton.setImageResource(favoriteIcon);

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavorite = ranking.isFavorite();

                if (isFavorite) {
                    removeFavorite(ranking);
                    showToast("Removed from favorites");
                } else {
                    addFavorite(ranking);
                    showToast("Added to favorites");
                }

                ranking.setFavorite(!isFavorite);

                int newFavoriteIcon = ranking.isFavorite() ? R.drawable.favoritefilled : R.drawable.favorite;
                holder.favoriteButton.setImageResource(newFavoriteIcon);

                if (context instanceof FavoriteFragment.FavoriteFragmentListener) {
                    ((FavoriteFragment.FavoriteFragmentListener) context).onFavoriteUpdated();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public void setRankings(List<Ranking> rankings) {
        rankingList = rankings;
        notifyDataSetChanged();
    }

    private boolean checkIfFavorite(int rankingId) {
        return sharedPreferences.getBoolean(String.valueOf(rankingId), false);
    }

    private void addFavorite(Ranking ranking) {
        editor.putBoolean(String.valueOf(ranking.getId()), true);
        editor.apply();
        updateFavoriteRankings(ranking, true);
    }

    private void removeFavorite(Ranking ranking) {
        editor.putBoolean(String.valueOf(ranking.getId()), false);
        editor.apply();
        updateFavoriteRankings(ranking, false);
    }

    private void updateFavoriteRankings(Ranking ranking, boolean add) {
        List<Ranking> favoriteRankings = getFavoriteRankingsFromSharedPreferences();
        if (add) {
            favoriteRankings.add(ranking);
        } else {
            for (int i = 0; i < favoriteRankings.size(); i++) {
                if (favoriteRankings.get(i).getId() == ranking.getId()) {
                    favoriteRankings.remove(i);
                    break;
                }
            }
        }
        String jsonFavorites = gson.toJson(favoriteRankings);
        editor.putString("favorite_rankings", jsonFavorites);
        editor.apply();
    }

    private List<Ranking> getFavoriteRankingsFromSharedPreferences() {
        String jsonFavorites = sharedPreferences.getString("favorite_rankings", null);
        if (jsonFavorites != null) {
            Type type = new TypeToken<List<Ranking>>() {}.getType();
            return gson.fromJson(jsonFavorites, type);
        } else {
            return new ArrayList<>();
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static class RankingViewHolder extends RecyclerView.ViewHolder {
        TextView rankingTextView;
        TextView pointTextView;
        TextView nameTextView;
        TextView countryTextView;
        ImageView favoriteButton;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            pointTextView = itemView.findViewById(R.id.pointTextView);
            rankingTextView = itemView.findViewById(R.id.ranking);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            countryTextView = itemView.findViewById(R.id.alpha3TextView1);
            favoriteButton = itemView.findViewById(R.id.favoriteImageView);
        }

        public void bind(Ranking ranking) {
            pointTextView.setText(String.valueOf(ranking.getPoints()));
            rankingTextView.setText(String.valueOf(ranking.getRanking()));
            nameTextView.setText(ranking.getRowName());
            if (ranking.getCountry() != null) {
                countryTextView.setText(ranking.getCountry().getName());
            } else {
                countryTextView.setText("Unknown");
            }

            // Add logging to debug
            Log.d("RankingAdapter", "Binding data: " + ranking.getRowName() + " - " + ranking.getRanking());
            if (ranking.getCountry() != null) {
                Log.d("RankingAdapter", "Country: " + ranking.getCountry().getName());
            }
        }
    }
}

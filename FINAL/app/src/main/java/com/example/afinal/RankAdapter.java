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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private List<Rank> rankList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private ExecutorService executorService;

    public RankAdapter(Context context, List<Rank> ranks) {
        this.context = context;
        this.rankList = ranks;
        this.sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.gson = new Gson();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        Rank rank = rankList.get(position);
        holder.bind(rank);

        boolean isFavorite = checkIfFavorite(rank.getId());
        rank.setFavorite(isFavorite);

        int favoriteIcon = rank.isFavorite() ? R.drawable.favoritefilled : R.drawable.favorite;
        holder.favoriteButton.setImageResource(favoriteIcon);

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavorite = rank.isFavorite();

                if (isFavorite) {
                    removeFavorite(rank);
                    showToast("Removed from favorites");
                } else {
                    addFavorite(rank);
                    showToast("Added to favorites");
                }

                rank.setFavorite(!isFavorite);

                int newFavoriteIcon = rank.isFavorite() ? R.drawable.favoritefilled : R.drawable.favorite;
                holder.favoriteButton.setImageResource(newFavoriteIcon);

                if (context instanceof FavoriteFragment.FavoriteFragmentListener) {
                    ((FavoriteFragment.FavoriteFragmentListener) context).onFavoriteUpdated();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    public void setRanks(List<Rank> ranks) {
        rankList = ranks;
        notifyDataSetChanged();
    }

    private boolean checkIfFavorite(int rankId) {
        return sharedPreferences.getBoolean(String.valueOf(rankId), false);
    }

    private void addFavorite(Rank rank) {
        editor.putBoolean(String.valueOf(rank.getId()), true);
        editor.apply();
        updateFavoriteRanks(rank, true);
    }

    private void removeFavorite(Rank rank) {
        editor.putBoolean(String.valueOf(rank.getId()), false);
        editor.apply();
        updateFavoriteRanks(rank, false);
    }

    private void updateFavoriteRanks(Rank rank, boolean add) {
        List<Rank> favoriteRanks = getFavoriteRanksFromSharedPreferences();
        if (add) {
            favoriteRanks.add(rank);
        } else {
            for (int i = 0; i < favoriteRanks.size(); i++) {
                if (favoriteRanks.get(i).getId() == rank.getId()) {
                    favoriteRanks.remove(i);
                    break;
                }
            }
        }
        String jsonFavorites = gson.toJson(favoriteRanks);
        editor.putString("favorite_ranks", jsonFavorites);
        editor.apply();
    }

    private List<Rank> getFavoriteRanksFromSharedPreferences() {
        String jsonFavorites = sharedPreferences.getString("favorite_ranks", null);
        if (jsonFavorites != null) {
            Type type = new TypeToken<List<Rank>>() {}.getType();
            return gson.fromJson(jsonFavorites, type);
        } else {
            return new ArrayList<>();
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static class RankViewHolder extends RecyclerView.ViewHolder {
        TextView rankingTextView;
        TextView pointTextView;
        TextView nameTextView;
        TextView countryTextView;
        ImageView favoriteButton;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            pointTextView = itemView.findViewById(R.id.pointTextView);
            rankingTextView = itemView.findViewById(R.id.ranking);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            countryTextView = itemView.findViewById(R.id.countryTextView);
            favoriteButton = itemView.findViewById(R.id.favoriteImageView);
        }

        public void bind(Rank rank) {
            pointTextView.setText(String.valueOf(rank.getPoints()));
            rankingTextView.setText(String.valueOf(rank.getRanking()));
            nameTextView.setText(rank.getRowName());
            if (rank.getTeam() != null && rank.getTeam().getCountry() != null) {
                countryTextView.setText(rank.getTeam().getCountry().getName());
            } else {
                countryTextView.setText("Unknown");
            }

            // Add logging to debug
            Log.d("RankAdapter", "Binding data: " + rank.getRowName() + " - " + rank.getRanking());
            if (rank.getTeam() != null) {
                Log.d("RankAdapter", "Team: " + rank.getTeam().getName());
                if (rank.getTeam().getCountry() != null) {
                    Log.d("RankAdapter", "Country: " + rank.getTeam().getCountry().getName());
                }
            }
        }
    }
}

package com.example.afinal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.Rank;
import com.example.afinal.RankAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment{
    private RecyclerView rvFavorite;
    private RankAdapter favoriteAdapter;
    private List<Rank> favoriteRanks;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private FavoriteFragmentListener listener;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorite = view.findViewById(R.id.rv_favorites);
        sharedPreferences = getContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        gson = new Gson();

        favoriteRanks = getFavoriteRanksFromSharedPreferences();

        favoriteAdapter = new RankAdapter(getContext(), favoriteRanks);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvFavorite.setAdapter(favoriteAdapter);

        return view;
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

    @Override
    public void onResume() {
        super.onResume();
        favoriteRanks = getFavoriteRanksFromSharedPreferences();
        favoriteAdapter.setRanks(favoriteRanks);
    }

    public interface FavoriteFragmentListener {
        void onFavoriteUpdated();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FavoriteFragmentListener) {
            listener = (FavoriteFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FavoriteFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void onFavoriteUpdated() {
        if (listener != null) {
            favoriteRanks = getFavoriteRanksFromSharedPreferences();
            favoriteAdapter.setRanks(favoriteRanks);
        }

    }
}

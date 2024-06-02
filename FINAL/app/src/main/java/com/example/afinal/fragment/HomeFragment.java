package com.example.afinal.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.afinal.LoginActivity;
import com.example.afinal.R;
import com.example.afinal.RankActivity;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private LottieAnimationView progressBar;
    private View contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = contentView.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        progressBar = contentView.findViewById(R.id.progressBar);

        // Menunda tampilan ProgressBar selama 3 detik sebelum memulai animasi
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.playAnimation();
                progressBar.setVisibility(View.GONE);
                contentView.findViewById(R.id.main).setVisibility(View.VISIBLE);
            }
        }, 2000); //

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logout) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });

        sharedPreferences = getActivity().getSharedPreferences("USER", getActivity().MODE_PRIVATE);

        CardView register1 = contentView.findViewById(R.id.register1_card);
        CardView register2 = contentView.findViewById(R.id.register2_card);

        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Country clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RankActivity.class);
                intent.putExtra("dataType", "country");
                startActivity(intent);
            }
        });

        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Club clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RankActivity.class);
                intent.putExtra("dataType", "club");
                startActivity(intent);
            }
        });

        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


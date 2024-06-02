package com.example.afinal;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.afinal.fragment.FavoriteFragment;
import com.example.afinal.fragment.HomeFragment;
import com.example.afinal.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements FavoriteFragment.FavoriteFragmentListener {

    private MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meowBottomNavigation = findViewById(R.id.meow_bottom_navigation);

        // Add navigation items
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_favorite_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.person));

        // Set default fragment
        loadFragment(new HomeFragment());

        // Set listener for navigation item click
        meowBottomNavigation.setOnClickMenuListener(model -> {
            Fragment fragment = null;
            switch (model.getId()) {
                case 1:
                    fragment = new HomeFragment();
                    break;
                case 2:
                    fragment = new FavoriteFragment();
                    break;
                case 3:
                    fragment = new ProfileFragment();
                    break;
            }
            loadFragment(fragment);
            return null;
        });

        // Set listener for default selected item
        meowBottomNavigation.setOnShowListener(model -> {
            Fragment fragment = null;
            switch (model.getId()) {
                case 1:
                    fragment = new HomeFragment();
                    break;
                case 2:
                    fragment = new FavoriteFragment();
                    break;
                case 3:
                    fragment = new ProfileFragment();
                    break;
            }
            loadFragment(fragment);
            return null;
        });

        // Set the first selected item
        meowBottomNavigation.show(1, true);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }
    @Override
    public void onFavoriteUpdated() {
        // Handle the update here
        // This method will be called whenever the favorites are updated in ProfileFragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof FavoriteFragment) {
            ((FavoriteFragment) currentFragment).onFavoriteUpdated();
        }
    }
}

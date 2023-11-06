package com.example.ecoapp.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.ActivityMainBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.services.NetworkChangeReceiver;

public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    public NavController navController;
    public StorageHandler storageHandler;
    public NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onRestart() {
        super.onRestart();

        storageHandler = new StorageHandler(getApplicationContext());
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        storageHandler = new StorageHandler(getApplicationContext());
        binding.setThemeInfo(storageHandler.getTheme());

        if (storageHandler.getTheme() == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (storageHandler.getTheme() == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    public void changeMenu(boolean isShow) {
        binding.bottomNavigationView.setVisibility(isShow ? View.VISIBLE : View.GONE);

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            NavigationUI.onNavDestinationSelected(item, navController);

            return true;
        });
    }

    private void initData() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        networkChangeReceiver = new NetworkChangeReceiver(new NetworkChangeReceiver.NetworkChangeListener() {
            @Override
            public void onNetworkConnected() {
                if (storageHandler.getAuth()) {
                    if (navController.getCurrentDestination().getId() == R.id.noNetworkFragment) {
                        changeMenu(true);
                        navController.navigate(R.id.homeFragment);
                    }
                } else changeMenu(false);
            }

            @Override
            public void onNetworkDisconnected() {
                changeMenu(false);
                navController.navigate(R.id.noNetworkFragment);
            }
        });
    }
}
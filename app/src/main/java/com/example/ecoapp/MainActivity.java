package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecoapp.Adapter.AdviceAdapter;
import com.example.ecoapp.Adapter.NearbyAdapter;
import com.example.ecoapp.Model.Advice;
import com.example.ecoapp.Model.Nearby;
import com.example.ecoapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ProfileFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemID = item.getItemId();
            if (itemID == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemID == R.id.profile) {
                replaceFragment(new ProfileFragment());
            } else if (itemID == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (itemID == R.id.events) {
                replaceFragment(new EventsFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
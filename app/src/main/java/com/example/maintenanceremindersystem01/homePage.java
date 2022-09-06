package com.example.maintenanceremindersystem01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.maintenanceremindersystem01.databinding.ActivityHomePageBinding;

public class homePage extends AppCompatActivity {

    ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ProductFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.product:
                    replaceFragment(new ProductFragment());
                    break;
                case R.id.chat:
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.srtTeam:
                    replaceFragment(new SRTTeamFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;


            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}
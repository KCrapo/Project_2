package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_2.R;
import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;
import com.example.project_2.databinding.ActivityAdminBinding;
import com.example.project_2.databinding.ActivityCreateAccountBinding;
import com.example.project_2.databinding.ActivityLoginBinding;
import com.example.project_2.databinding.ActivityMainBinding;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;

    private CharacterTrackerRepository repository;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initial setup ///
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize repository
        repository = CharacterTrackerRepository.getRepository(getApplication());

        //Code to keep track of account using
        int userId = getIntent().getIntExtra("USER_ID", -1);
        if (userId != -1) {
            repository.getUserByUserId(userId).observe(this, user -> {
                this.user = user;
            });
        }

        //Button Hookups

        // Delete Button
        binding.deleteUserAdminActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMaker("Button Working");

            }
        });

        // Home Button
        binding.homeButtonAdminActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),userId));
            }
        });

    }

    // Intent Factory

    static Intent AdminIntentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
    }

    //Helper Functions

    // In the future I would like to make this function in 1 place and let all of our activities use it
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
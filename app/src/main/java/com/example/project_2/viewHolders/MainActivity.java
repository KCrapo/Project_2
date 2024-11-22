package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_2.databinding.ActivityMainBinding;
import com.example.project_2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private static final String MAIN_ACTIVITY_USER_ID ="com.example.project_2.viewHolders.MAIN_ACTIVITY_USER_ID" ;
    int loggedInUserId = -1;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initial setup ///
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Login Screen
        loginUser();

        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }


        // Login Button Connection
        binding.selectCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMaker("View Character Button Working!");
            }
        });

        // Create Account Button Connection
        binding.createCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMaker("Create Character Button Working!");
            }
        });

        // Admin Button Connection
        // Create Account Button Connection
        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMaker("Admin Button Working!");
            }
        });

    }

    private void loginUser() {

    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
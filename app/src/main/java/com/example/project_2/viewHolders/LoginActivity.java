package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project_2.database.entities.User;
import com.example.project_2.databinding.ActivityLoginBinding;
import com.example.project_2.typeConverters.CharacterTrackerRepository;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private CharacterTrackerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initial setup ///
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize repository
        repository = CharacterTrackerRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               verifyUser();
            }
        });


        // Create Account Button Connection
        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMaker("Create Account Button Working!");
            }
        });

    }

    //Intent Factory

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    // Login Functions

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username should not be blank");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    toastMaker("Invalid Password");
                    binding.passwordLoginEditText.setSelection(0);

                }

            } else {
                toastMaker(String.format("No user %s is a valid username", username));
                binding.passwordLoginEditText.setSelection(0);
            }
        });


    }

    // Helper Functions
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project_2.R;
import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;
import com.example.project_2.database.typeConverters.UserDAO;
import com.example.project_2.databinding.ActivityCreateAccountBinding;


public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private CharacterTrackerRepository repository;
    private static volatile CharacterTrackerDatabase INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initial setup ///
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize repository
        repository = CharacterTrackerRepository.getRepository(getApplication());

        //Button Hookups

        binding.signUpCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        binding.homeButtonCreateAccountActivity.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        }));
    }

    //Intent Factory I dont think I need to pass any extras
    static Intent createAccountIntentFactory(Context context) {
        return new Intent(context, CreateAccountActivity.class);
    }

    // Helper Functions
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Account creation function
    private void createAccount() {
        String username = binding.userNameCreateAccountEditText.getText().toString();
        String password = binding.passwordCreateAccountEditText.getText().toString();
        String rPassword = binding.reEnterPasswordCreateAccountEditText.getText().toString();

        // Check if any of the fields are empty
        if (username.isEmpty() || password.isEmpty() || rPassword.isEmpty()) {
            toastMaker("Please fill all fields.");
            return;
        }

        // Check if password and re-entered password match
        if (!password.equals(rPassword)) {
            toastMaker("Passwords do not match.");
            binding.passwordCreateAccountEditText.setSelection(0);
            binding.reEnterPasswordCreateAccountEditText.setSelection(0);
            return;
        }

        // Check if the username already exists in the database
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                toastMaker("Username is already taken.");
                binding.userNameCreateAccountEditText.setSelection(0);

            } else {
                // Create a new User and insert into the database
                User newUser = new User(username, password);
                repository.insertUser(newUser);


                // Navigate to login screen after account creation
                toastMaker("Account created successfully!");
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
            // Username is already taken was still popping up after success. Did some research and I
            // think that this will fix it.
            userObserver.removeObservers(this);
        });

    }
}

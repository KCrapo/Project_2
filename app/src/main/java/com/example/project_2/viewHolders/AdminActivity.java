package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project_2.R;
import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;
import com.example.project_2.databinding.ActivityAdminBinding;

import java.util.ArrayList;
import java.util.List;

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

        //Code to keep track of userID for returning to landing page.
        int userId = getIntent().getIntExtra("USER_ID", -1);
        if (userId != -1) {
            repository.getUserByUserId(userId).observe(this, user -> {
                this.user = user;
            });
        }

        // display usernames
        updateDropdownUsernames();

        //Button Hookups

        // Delete Button
        binding.deleteUserAdminActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete user
                deleteUser();
                updateDropdownUsernames();

            }
        });

        // Home Button
        binding.homeButtonAdminActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), userId));
            }
        });

    }

    // Intent Factory

    static Intent AdminIntentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
    }

    //Helper Functions

    private void deleteUser() {

        // Get the selected username from the Spinner
        Spinner spinner = findViewById(R.id.userNameDropDown);
        String selectedUsername = spinner.getSelectedItem().toString();

        // Check if the selected username is valid (non-empty)
        if (selectedUsername.isEmpty() || selectedUsername.equals("No users found")) {
            toastMaker("No valid username selected");
            return;
        }


        // Then I want to make sure that the verified username exists in the database
        LiveData<User> userObserver = repository.getUserByUserName(selectedUsername);
        userObserver.observe(this, user -> {
            if (user == null) {
                // If the user does not exist, show a message
                toastMaker("User does not exist");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setPositiveButton("Delete User", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User taps OK button.
                        repository.deleteUser(user);
                        toastMaker("User has been deleted");


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog.
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            userObserver.removeObservers(this);
        });


    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //Display

    private void displayUsernames() {
        repository.getAllUsers().observe(this, users -> {
            // check to see if list is empty or null
            if (users != null && !users.isEmpty()) {
                StringBuilder usernameList = new StringBuilder();


                for (User user : users) {
                    usernameList.append(user.getUserName()).append("\n");
                }
                // Get the TextView and set the concatenated string
                TextView textView = findViewById(R.id.adminActivityDisplayLabel);
                textView.setText(usernameList.toString());

                //Make it scrollable
                binding.adminActivityDisplayLabel.setMovementMethod(new ScrollingMovementMethod());
            } else {
                TextView textView = findViewById(R.id.adminActivityDisplayLabel);
                textView.setText("No users found");

                //Make it scrollable
                binding.adminActivityDisplayLabel.setMovementMethod(new ScrollingMovementMethod());
            }
        });


    }

    private void updateDropdownUsernames() {
        repository.getAllUsers().observe(this, users -> {
            List<String> userList = new ArrayList<>();
            userList.add("Username");
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
                    userList.add(user.getUserName());
                }

                // Create an arrayadapter to add user list to dropdown
                ArrayAdapter<String> userAdapter = new ArrayAdapter<>(AdminActivity.this,
                        android.R.layout.simple_spinner_item, userList);

                //Set the layout for the dropdown
                userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //
                binding.userNameDropDown.setAdapter(userAdapter);
            }
        });
    }


}
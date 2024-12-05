package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project_2.R;
import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.User;
import com.example.project_2.databinding.ActivityMainBinding;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project_2.viewHolders.MAIN_ACTIVITY_USER_ID";
    public static final String TAG = "DAC_CHARACTER_CREATOR";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.project_2.viewHolders.SAVED_INSTANCE_STATE_USERID_KEY";
    int loggedInUserId = -1;
    private User user;
    private ActivityMainBinding binding;

    private CharacterTrackerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initial setup ///
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // getting an instance of the database

        repository = CharacterTrackerRepository.getRepository(getApplication());


        //Login Screen
        loginUser(savedInstanceState);

        updateCharacterDropDown();

        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }


        // Login Button Connection
        binding.selectCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = CharacterViewActivity.characterViewIntentFactory(getApplicationContext(), loggedInUserId);
//                startActivity(intent);
                  selectCharacter();

            }
        });

        // Create Account Button Connection
        binding.createCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CharacterCreationActivity.characterCreationIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
            }
        });

        // Admin Button Connection
        // Create Account Button Connection
        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(AdminActivity.AdminIntentFactory(getApplicationContext()));

            }
        });

    }

    private void adminCheck() {
        if (this.user != null && this.user.isAdmin()) {
            binding.adminButton.setVisibility(View.VISIBLE);
        } else {
            binding.adminButton.setVisibility(View.INVISIBLE);
        }
    }

    private void loginUser(Bundle savedInstanceState) {

        // check shared preference for loggedInUser
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);


        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);


        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = sharedPreferences.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
                adminCheck();
            }
        });
    }

    //Helper Functions and Intents

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /// OPTIONS MENU CODE////////////

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if (user == null) {
            return false;
        }
        item.setTitle(user.getUserName());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                showLogoutDialogue();
                return false;
            }
        });
        return true;

    }

    ///////// Logout Stuff /////////////////

    private void showLogoutDialogue() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    /// Shared Preferences /////


    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }


    /// Dropdown Character Selection Menu

    /**
     * Fixed the issues with updateCharacterDropDown. Honestly there was too much going on with having two observers going on at the same time
     * I then realized that we already have the loggedInUserId updating from shared preferences. So we can just use that instead.
     */
    private void updateCharacterDropDown() {
        // Check if the logged-in userId is valid
        if (loggedInUserId != -1) {
            // Fetch the user data by loggedInUserId
            repository.getUserByUserId(loggedInUserId).observe(this, user -> {
                if (user != null) {
                    this.user = user; // Store the user object

                    // Now that we have the user, fetch the characters for this user
                    repository.getAllCharactersByUserId(user.getId()).observe(this, characters -> {
                        List<String> characterList = new ArrayList<>();

                        // Add placeholder values for the dropdown
                        characterList.add("User Characters");

                        // If characters are found, add them to the list
                        if (characters != null && !characters.isEmpty()) {
                            for (DNDCharacter character : characters) {
                                characterList.add(character.getName());
                            }
                        }

                        // Create and set up the dropdown (Spinner) for characters
                        ArrayAdapter<String> characterAdapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_spinner_item, characterList);
                        characterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Set the adapter to the Spinner
                        binding.CharacterSelectionSpinner.setAdapter(characterAdapter);
                    });
                } else {
                    // If the user is not found, show a message (invalid username)
                    toastMaker("User not found.");
                }
            });
        } else {
            // Invalid ID
            toastMaker("Invalid userId.");
        }
    }

    // select a Character and "pass" the details into CharacterView Activity
    private void selectCharacter(){
        String selectedChar = binding.CharacterSelectionSpinner.getSelectedItem().toString();
        if (selectedChar.isEmpty()|| selectedChar.equals("User Characters")){
            toastMaker("Please select a Character");
            return;
        }
        repository.getCharacterByName(selectedChar).observe(this, character -> {
            if (character != null) {
                // If the character is found, start the CharacterViewActivity with the character's details
                Intent intent = CharacterViewActivity.characterViewIntentFactory(getApplicationContext(), character.getCharacterId());
                startActivity(intent);
            } else {
                // If no character is found, show an error message
                toastMaker("Character not found.");
            }
        });
    }


}
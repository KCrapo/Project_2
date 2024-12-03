package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

        //Code to keep track of userID for returning to landing page.
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
                deleteUser();

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

    // In the future I would like to make this function in 1 place and let all of our activities use it
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Delete User

    private void deleteUser() {

        String username = binding.usernameToDeleteEditText.getText().toString();
        String rUsername = binding.usernameToDeleteConfirmEditText.getText().toString();


        // First I need to verify that username and re-entered username are the same
        // if not return and maketoast("Usernames do not match")
        if (!username.equals(rUsername)) {
            toastMaker("Usernames do not match");
            return;
        }

        // Then I want to make sure that the verified username exists in the database

        repository.getUserByUserName(username).observe(this, user -> {
            if (user == null) {
                // If the user does not exist, show a message
                toastMaker("User does not exist");
            } else {
                // THIS IS WHERE I WANT TO ADD AN ALERT DIALOG

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                AlertDialog dialog = builder.create();
                builder.setPositiveButton("Delete User", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User taps OK button.
                        repository.deleteUser(user);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog.
                        dialog.dismiss();
                    }
                });




                // Delete user
            }
        });


    }


    //Display
    //      REMEMBER THIS LINE TO ADD SCROLLING
    //binding.adminActivityDisplayLabel.setMovementMethod(new ScrollingMovementMethod());
}
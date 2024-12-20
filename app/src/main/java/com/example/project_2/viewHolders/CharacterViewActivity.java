package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_2.R;
import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;
import com.example.project_2.databinding.ActivityCharacterViewBinding;

import java.util.ArrayList;
import java.util.List;

public class CharacterViewActivity extends AppCompatActivity {

    private ActivityCharacterViewBinding binding;
    private CharacterTrackerRepository repository;
    private User user;
    DNDCharacter character;

    public static final String CHARACTER_VIEW_CHARACTER_ID = "com.example.project_2.viewHolders.CHARACTER_VIEW_CHARACTER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Setup
        super.onCreate(savedInstanceState);
        binding = ActivityCharacterViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Repository
        repository = CharacterTrackerRepository.getRepository(getApplication());

        // Tracking userId for home button
        int userId = getIntent().getIntExtra("USER_ID", -1);
        if (userId != -1) {
            repository.getUserByUserId(userId).observe(this, user -> {
                this.user = user;
            });
        }

        // Get the characterId from the Intent
        int characterId = getIntent().getIntExtra(CHARACTER_VIEW_CHARACTER_ID, -1);
        if (characterId != -1) {
            repository.getCharacterByCharacterId(characterId).observe(this, character -> {
                if (character != null) {
                    this.character = character;
                    displayCharacterDetails(character);  // Display character's details
                } else {
                    toastMaker("Character not found.");
                }
            });
        } else {
            toastMaker("Invalid characterId.");
        }

        // Button hookup
        binding.CharacterViewInventoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), "Inventory button working!", Toast.LENGTH_SHORT).show();
                replaceFragment(new InventoryFragment());
            }
        });

        binding.CharacterViewSpellBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SpellbookFragment());
            }
        });

        binding.CharacterViewHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), userId);
                startActivity(intent);
            }
        });



    }


    // Character Display
    private void displayCharacterDetails(DNDCharacter character) {

        // Should probably use string resources here, buuuuut this will do for now
        binding.characterViewNameTextView.setText(character.getName() +
                " - Level " + character.getLevel() +
                " " + character.getRace() +
                " " + character.getCharacterClass());

        binding.CharacterViewStrengthTextViewOutput.setText(String.valueOf(character.getStrength()));
        binding.CharacterViewDexterityTextViewOutput.setText(String.valueOf(character.getDexterity()));
        binding.CharacterViewConstitutionTextViewOutput.setText(String.valueOf(character.getConstitution()));
        binding.CharacterViewIntelligenceTextViewOutput.setText(String.valueOf(character.getIntelligence()));
        binding.CharacterViewWisdomTextViewOutput.setText(String.valueOf(character.getWisdom()));
        binding.CharacterViewCharismaTextViewOutput.setText(String.valueOf(character.getCharisma()));
    }

    // Intent Factory

    // In retrospect we probably should have added userID as an extra... but we had it stored in
    // shared preferences so I just used that when both are needed

    public static Intent characterViewIntentFactory(Context context, int characterId) {
        Intent intent = new Intent(context, CharacterViewActivity.class);
        intent.putExtra(CHARACTER_VIEW_CHARACTER_ID, characterId);
        return intent;
    }

    // Helper Functions

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Replacing fragment layout - woohoo!

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.CharacterViewFragment,fragment);
        fragmentTransaction.commit();
    }

}
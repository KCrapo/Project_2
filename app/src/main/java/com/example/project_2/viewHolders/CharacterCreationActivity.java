package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_2.R;
import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;
import com.example.project_2.databinding.ActivityCharacterCreationBinding;

public class CharacterCreationActivity extends AppCompatActivity {
    private ActivityCharacterCreationBinding binding;
    private CharacterTrackerRepository repository;
    private User user;
    private DNDCharacter character;

    private int currentStatEntryMethod = NONE;

    //Private static final variables to avoid hard coding
    private static final int NOT_LOGGED_IN = -1;
    private static final int NONE = 0;
    private static final int CUSTOM_ENTRY = 1;
    private static final int STANDARD_ARRAY = 2;
    private static final int ROLL_STATS = 3;
    private static final int DEFAULT_LEVEL = 1;
    private static final String NOT_IMPLEMENTED = "NOT IMPLEMENTED IN THIS VERSION";
    private static final String CHARACTER_CREATOR_USER_ID = "com.example.project_2.viewHolders.CHARACTER_CREATOR_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharacterCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CharacterTrackerRepository.getRepository(getApplication());

        //creating the drop down menus for class and race
        ArrayAdapter<CharSequence> classesAdapter = ArrayAdapter.createFromResource(this, R.array.character_classes, android.R.layout.simple_spinner_item);
        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.characterClassSpinner.setAdapter(classesAdapter);

        ArrayAdapter<CharSequence> racesAdapter = ArrayAdapter.createFromResource(this, R.array.character_race, android.R.layout.simple_spinner_item);
        racesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.characterRaceSpinner.setAdapter(racesAdapter);

        //sets the visibility of the stat entry fields to invisible until a button is pressed
        setStatVisibility(NONE);

        //checks if a user is logged in and if so, gets the user
        int userId = getIntent().getIntExtra(CHARACTER_CREATOR_USER_ID, NOT_LOGGED_IN);
        if (userId != -1) {
            repository.getUserByUserId(userId).observe(this, user -> {
                this.user = user;
            });
        }

        //Button Hookups
        // Create Character Button
        binding.createCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForEmptyFields()) {
                    collectCharacterData();
                    addCharacterToDatabase();
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
            }
        });

        // Custom Entry Button - sets the stat entry method to manual entry by the user
        binding.customEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatVisibility(CUSTOM_ENTRY);
                currentStatEntryMethod = CUSTOM_ENTRY;
            }
        });

        // Standard Array Button - sets the stat entry method to the standard array with drop down menus, not implemented
        binding.standardArrayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
                setStatVisibility(STANDARD_ARRAY);
                currentStatEntryMethod = STANDARD_ARRAY;
            }
        });

        // Roll Stats Button - sets the stat entry method to rolling stats with buttons for dice rolls, not implemented
        binding.rollStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
                setStatVisibility(ROLL_STATS);
                currentStatEntryMethod = ROLL_STATS;
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                startActivity(intent);
            }
        });
    }

    //this probably isn't necessary as a separate function, but it's here for now
    private void addCharacterToDatabase() {
        repository.insertCharacter(character);
    }

    private boolean checkForEmptyFields() {
        //doesnt check for empty bonuses because they are not required
        if (binding.characterNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a character name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.characterClassSpinner.getSelectedItem().toString().equals("Select a Class")) {
            Toast.makeText(getApplicationContext(), "Please select a character class", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.characterRaceSpinner.getSelectedItem().toString().equals("Select a Race")) {
            Toast.makeText(getApplicationContext(), "Please select a character race", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(currentStatEntryMethod == NONE){
            Toast.makeText(getApplicationContext(), "Please select a stat entry method and enter stats", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(currentStatEntryMethod == CUSTOM_ENTRY){
            if(binding.strengthEditText.getText().toString().isEmpty() || binding.dexterityEditText.getText().toString().isEmpty() || binding.constitutionEditText.getText().toString().isEmpty() || binding.intelligenceEditText.getText().toString().isEmpty() || binding.wisdomEditText.getText().toString().isEmpty() || binding.charismaEditText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a strength stat", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(binding.dexterityEditText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a dexterity stat", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(binding.constitutionEditText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a constitution stat", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(binding.intelligenceEditText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter an intelligence stat", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(binding.wisdomEditText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a wisdom stat", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(binding.charismaEditText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a charisma stat", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(currentStatEntryMethod == STANDARD_ARRAY){
            Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(currentStatEntryMethod == ROLL_STATS){
            Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void collectCharacterData() {
        String characterName = binding.characterNameEditText.getText().toString();
        String characterClass = binding.characterClassSpinner.getSelectedItem().toString();
        String characterRace = binding.characterRaceSpinner.getSelectedItem().toString();

        int characterStrength = 0;
        int characterDexterity = 0;
        int characterConstitution = 0;
        int characterIntelligence = 0;
        int characterWisdom = 0;
        int characterCharisma = 0;

        int strengthBonus = 0;
        int dexterityBonus = 0;
        int constitutionBonus = 0;
        int intelligenceBonus = 0;
        int wisdomBonus = 0;
        int charismaBonus = 0;

        //might need to be edited further in later versions
        if(!binding.strengthBonusEditText.getText().toString().isEmpty()){
            strengthBonus = Integer.parseInt(binding.strengthBonusEditText.getText().toString());
        }
        if(!binding.dexterityBonusEditText.getText().toString().isEmpty()){
            dexterityBonus = Integer.parseInt(binding.dexterityBonusEditText.getText().toString());
        }
        if(!binding.constitutionBonusEditText.getText().toString().isEmpty()){
            constitutionBonus = Integer.parseInt(binding.constitutionBonusEditText.getText().toString());
        }
        if(!binding.intelligenceBonusEditText.getText().toString().isEmpty()){
            intelligenceBonus = Integer.parseInt(binding.intelligenceBonusEditText.getText().toString());
        }
        if(!binding.wisdomBonusEditText.getText().toString().isEmpty()){
            wisdomBonus = Integer.parseInt(binding.wisdomBonusEditText.getText().toString());
        }
        if(!binding.charismaBonusEditText.getText().toString().isEmpty()){
            charismaBonus = Integer.parseInt(binding.charismaBonusEditText.getText().toString());
        }

        if(currentStatEntryMethod == CUSTOM_ENTRY){
            characterStrength = Integer.parseInt(binding.strengthEditText.getText().toString()) + strengthBonus;
            characterDexterity = Integer.parseInt(binding.dexterityEditText.getText().toString()) + dexterityBonus;
            characterConstitution = Integer.parseInt(binding.constitutionEditText.getText().toString()) + constitutionBonus;
            characterIntelligence = Integer.parseInt(binding.intelligenceEditText.getText().toString()) + intelligenceBonus;
            characterWisdom = Integer.parseInt(binding.wisdomEditText.getText().toString()) + wisdomBonus;
            characterCharisma = Integer.parseInt(binding.charismaEditText.getText().toString()) + charismaBonus;
        }
        if(currentStatEntryMethod == STANDARD_ARRAY){
            Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
            return;
        }
        if(currentStatEntryMethod == ROLL_STATS){
            Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
            return;
        }

        character = new DNDCharacter(characterName, characterRace, characterClass, DEFAULT_LEVEL, characterStrength, characterDexterity, characterConstitution, characterIntelligence, characterWisdom, characterCharisma, user.getId());
    }

    static Intent characterCreationIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CharacterCreationActivity.class);
        intent.putExtra(CHARACTER_CREATOR_USER_ID, userId);
        return intent;
    }

    private void setStatVisibility(int statEntryMethodId){
        switch (statEntryMethodId) {
            case NONE:
                statTitleVisibility(View.INVISIBLE);
                statTextViewVisibility(View.INVISIBLE);
                statEditTextVisibility(View.INVISIBLE);
                statBonusEditTextVisibility(View.INVISIBLE);
                break;
            case CUSTOM_ENTRY:
                statTitleVisibility(View.VISIBLE);
                statTextViewVisibility(View.VISIBLE);
                statEditTextVisibility(View.VISIBLE);
                statBonusEditTextVisibility(View.VISIBLE);
                break;
            case STANDARD_ARRAY:
                statTitleVisibility(View.VISIBLE);
                statTextViewVisibility(View.VISIBLE);
                statEditTextVisibility(View.INVISIBLE);
                statBonusEditTextVisibility(View.VISIBLE);
                break;
            case ROLL_STATS:
                statTitleVisibility(View.VISIBLE);
                statTextViewVisibility(View.VISIBLE);
                statEditTextVisibility(View.INVISIBLE);
                statBonusEditTextVisibility(View.VISIBLE);
                break;
        }
    }

    private void statTextViewVisibility(int visible){
        binding.strengthTextView.setVisibility(visible);
        binding.dexterityTextView.setVisibility(visible);
        binding.constitutionTextView.setVisibility(visible);
        binding.intelligenceTextView.setVisibility(visible);
        binding.wisdomTextView.setVisibility(visible);
        binding.charismaTextView.setVisibility(visible);
    }

    private void statEditTextVisibility(int visible){
        binding.strengthEditText.setVisibility(visible);
        binding.dexterityEditText.setVisibility(visible);
        binding.constitutionEditText.setVisibility(visible);
        binding.intelligenceEditText.setVisibility(visible);
        binding.wisdomEditText.setVisibility(visible);
        binding.charismaEditText.setVisibility(visible);
    }

    private void statBonusEditTextVisibility(int visible){
        binding.strengthBonusEditText.setVisibility(visible);
        binding.dexterityBonusEditText.setVisibility(visible);
        binding.constitutionBonusEditText.setVisibility(visible);
        binding.intelligenceBonusEditText.setVisibility(visible);
        binding.wisdomBonusEditText.setVisibility(visible);
        binding.charismaBonusEditText.setVisibility(visible);
    }

    private void statTitleVisibility(int visible){
        binding.statsTextView.setVisibility(visible);
        binding.baseValueTextView.setVisibility(visible);
        binding.bonusesTextView.setVisibility(visible);
    }

}
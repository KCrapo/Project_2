package com.example.project_2.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;
import com.example.project_2.databinding.ActivityCharacterCreationBinding;

public class CharacterCreationActivity extends AppCompatActivity {
    private ActivityCharacterCreationBinding binding;
    private CharacterTrackerRepository repository;
    private User user;
    //Private static final variables to avoid hard coding
    private static final int NOT_LOGGED_IN = -1;
    private static final int NONE = 0;
    private static final int CUSTOM_ENTRY = 1;
    private static final int STANDARD_ARRAY = 2;
    private static final int ROLL_STATS = 3;
    private static final String NOT_IMPLEMENTED = "NOT IMPLEMENTED IN THIS VERSION";
    private static final String CHARACTER_CREATOR_USER_ID = "com.example.project_2.viewHolders.CHARACTER_CREATOR_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharacterCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CharacterTrackerRepository.getRepository(getApplication());

        setStatVisibility(NONE);

        int userId = getIntent().getIntExtra(CHARACTER_CREATOR_USER_ID, NOT_LOGGED_IN);
        if (userId != -1) {
            repository.getUserByUserId(userId).observe(this, user -> {
                this.user = user;
            });
        }

        binding.createCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Create Character Button Working!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.customEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Custom Entry Button Working!", Toast.LENGTH_SHORT).show();
                setStatVisibility(CUSTOM_ENTRY);
            }
        });

        binding.standardArrayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
            }
        });
        binding.rollStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
            }
        });

    }

    static Intent characterCreationIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CharacterCreationActivity.class);
        intent.putExtra(CHARACTER_CREATOR_USER_ID, userId);
        return intent;
    }

    private void setStatVisibility(int statEntryMethodId){
        switch (statEntryMethodId) {
            case NONE:
                statTextViewVisibility(View.INVISIBLE);
                statEditTextVisibility(View.INVISIBLE);

                break;
            case CUSTOM_ENTRY:
                statTextViewVisibility(View.VISIBLE);
                statEditTextVisibility(View.INVISIBLE);

                break;
            case STANDARD_ARRAY:
                statTextViewVisibility(View.VISIBLE);
                statEditTextVisibility(View.INVISIBLE);

                break;
            case ROLL_STATS:
                statTextViewVisibility(View.VISIBLE);
                statEditTextVisibility(View.INVISIBLE);

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

}
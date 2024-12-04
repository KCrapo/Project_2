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
    private DNDCharacter character;

    private static final String CHARACTER_VIEW_CHARACTER_ID = "com.example.project_2.viewHolders.CHARACTER_VIEW_CHARACTER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Setup
        super.onCreate(savedInstanceState);
        binding = ActivityCharacterViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Repository
        repository = CharacterTrackerRepository.getRepository(getApplication());

        // Button hookup
        binding.CharacterViewInventoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Inventory button working!", Toast.LENGTH_SHORT).show();
            }
        });

    }








    static Intent characterViewIntentFactory(Context context, int characterId) {
        Intent intent = new Intent(context, CharacterViewActivity.class);
        intent.putExtra(CHARACTER_VIEW_CHARACTER_ID, characterId);
        return intent;
    }

}
package com.example.project_2.viewHolders;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_2.R;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;
import com.example.project_2.databinding.ActivityAdminBinding;
import com.example.project_2.databinding.ActivityCreateAccountBinding;
import com.example.project_2.databinding.ActivityLoginBinding;
import com.example.project_2.databinding.ActivityMainBinding;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;

    private CharacterTrackerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initial setup ///
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize repository
        repository = CharacterTrackerRepository.getRepository(getApplication());

        //Button Hookups

    }
}
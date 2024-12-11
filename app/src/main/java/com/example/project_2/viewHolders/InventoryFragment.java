package com.example.project_2.viewHolders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_2.R;
import com.example.project_2.database.entities.InventoryItem;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;

import java.util.List;
import java.util.Objects;


public class InventoryFragment extends Fragment {

    private CharacterTrackerRepository repository;

    private InventoryViewModel inventoryViewModel;

    int characterId;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inventory, container, false);

        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);


        RecyclerView recyclerView = view.findViewById(R.id.inventoryFragmentRecyclerView);
        final InventoryAdapter adapter = new InventoryAdapter(new InventoryAdapter.InventoryDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CharacterViewActivity characterViewActivity = new CharacterViewActivity();
        characterId = characterViewActivity.getCharacterId();

        repository = CharacterTrackerRepository.getRepository(requireActivity().getApplication());
        List<InventoryItem> inventoryList = inventoryViewModel.getInventoryList(characterId);
        adapter.submitList(inventoryList);


        return view;
    }
}
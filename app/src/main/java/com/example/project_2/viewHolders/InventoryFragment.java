package com.example.project_2.viewHolders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_2.R;
import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.Inventory;
import com.example.project_2.database.entities.InventoryItem;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;

import java.util.ArrayList;
import java.util.List;


public class InventoryFragment extends Fragment {

    View view;

    TextView inventoryTextView;

    DNDCharacter character;

    CharacterTrackerRepository repository;

    StringBuilder inventoryString = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inventory, container, false);
        repository = CharacterTrackerRepository.getRepository(getActivity().getApplication());
        inventoryTextView = (TextView) view.findViewById(R.id.inventoryTextView);

        CharacterViewActivity activity = (CharacterViewActivity) getActivity();
        character = activity.character;



        if(character != null){

            repository.getInventoryByCharacterId(character.getCharacterId()).observe(getViewLifecycleOwner(), inventory -> {
                if(inventory != null){
                    List<InventoryItem> inventoryItems = new ArrayList<>();
                    for(Inventory inv: inventory){
                        repository.getInventoryItemByItemId(inv.getItemId()).observe(getViewLifecycleOwner(), inventoryItems::add);
                    }

                    for(InventoryItem item : inventoryItems){
                        inventoryString.append(item.toStringSummary()).append("\n");
                    }

                }
            });
        }






        inventoryTextView.setText(inventoryString.toString());



        return view;
    }
}
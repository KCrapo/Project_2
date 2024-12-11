package com.example.project_2.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.Inventory;
import com.example.project_2.database.entities.InventoryItem;
import com.example.project_2.database.typeConverters.CharacterTrackerRepository;

import java.util.List;

public class InventoryViewModel extends AndroidViewModel {
    private final CharacterTrackerRepository repository;
//    private final LiveData<List<Inventory>> inventory;
//    private final LiveData<List<InventoryItem>> inventoryList;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        repository = CharacterTrackerRepository.getRepository(application);
//        inventory = repository.getInventoryByCharacterId(characterId);


    }

    public LiveData<List<Inventory>> getInventory(int characterId) {
        return repository.getInventoryByCharacterId(characterId);
    }

    public List<InventoryItem> getInventoryList(int characterId) {
        return repository.getInventoryItemsByCharacterId(characterId);
    }



    public void insert(Inventory inventory) {
        repository.insertInventory(inventory);
    }

    public void delete(Inventory inventory) {
        repository.deleteInventory(inventory);
    }

    public void insert(InventoryItem inventoryItem) {
        repository.insertInventoryItem(inventoryItem);
    }

    public void delete(InventoryItem inventoryItem) {
        repository.deleteInventoryItem(inventoryItem);
    }
}

package com.example.project_2.database.typeConverters;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.Inventory;

import java.util.List;

@Dao
public interface InventoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Inventory inventory);

    @Delete
    void delete(Inventory inventory);

    @Query("DELETE from " + "inventorytable")
    void deleteAll();

    @Query("DELETE from " + "inventorytable" + " WHERE characterId == :characterId")
    void deleteInventoryByCharacterId(int characterId);

    @Query(" SELECT * FROM " + "inventorytable"+ " ORDER BY inventoryId")
    LiveData<List<Inventory>> getAllInventories();

    @Query("SELECT * from "+ "inventorytable"+ " WHERE characterId == :characterId")
    LiveData<List<Inventory>> getInventoryByCharacterId(int characterId);

    @Query("SELECT * from "+ "inventorytable"+ " WHERE itemId == :itemId")
    LiveData<Inventory> getInventoryByItemId(int itemId);

    @Query("SELECT * from "+ "inventorytable"+ " WHERE itemId == :itemId")
    LiveData<List<Inventory>> getInventoriesByItemId(int itemId);

    @Query("DELETE from " + "inventorytable" + " WHERE characterId == :characterId AND itemId == :itemId")
    void deleteInventoryByCharacterIdAndItemId(int characterId, int itemId);
}

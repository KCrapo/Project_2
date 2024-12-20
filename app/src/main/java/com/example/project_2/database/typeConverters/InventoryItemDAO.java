package com.example.project_2.database.typeConverters;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.InventoryItem;

import java.util.List;

@Dao
public interface InventoryItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InventoryItem...inventoryItem);

    @Delete
    void delete(InventoryItem inventoryItem);

    @Query("DELETE from " + "InventoryItemTable")
    void deleteAll();

    @Query("DELETE from " + "InventoryItemTable" + " WHERE itemId == :itemId")
    void deleteItemById(int itemId);

    @Query(" SELECT * FROM " + "InventoryItemTable"+ " ORDER BY itemId")
    LiveData<List<InventoryItem>> getAllItems();

    @Query("SELECT * from "+ "InventoryItemTable"+ " WHERE itemId == :itemId")
    LiveData<InventoryItem> getInventoryItemById(int itemId);

    @Query("SELECT * from "+ "InventoryItemTable"+ " WHERE itemName == :itemName")
    LiveData<InventoryItem> getInventoryItemByItemName(String itemName);

    @Query("SELECT * from "+ "InventoryItemTable"+ " WHERE itemCategory == :itemCategory")
    LiveData<List<InventoryItem>> getInventoryItemsByItemCategory(String itemCategory);

    @Query("SELECT * from "+ "InventoryItemTable"+ " WHERE itemRarity == :itemRarity")
    LiveData<List<InventoryItem>> getInventoryItemsByItemRarity(String itemRarity);
}

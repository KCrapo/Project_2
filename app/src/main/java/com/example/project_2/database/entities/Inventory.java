package com.example.project_2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;

import java.util.Objects;

@Entity(tableName = CharacterTrackerDatabase.INVENTORY_TABLE)
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    private int inventoryId;

    private int characterId;

    private int itemId;

    public Inventory(int characterId, int itemId) {
        this.characterId = characterId;
        this.itemId = itemId;
    }

    public int getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return inventoryId == inventory.inventoryId && characterId == inventory.characterId && itemId == inventory.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, characterId, itemId);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", characterId=" + characterId +
                ", itemId=" + itemId +
                '}';
    }
}

package com.example.project_2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;

import java.util.Objects;

@Entity(tableName = CharacterTrackerDatabase.INVENTORY_ITEM_TABLE)
public class InventoryItem {
    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private String itemName;

    private String itemDescription;

    private int itemWeight;

    private int itemValue;

    private String itemCategory;

    private int itemQuantity;

    private String itemRarity;

    public InventoryItem(String itemName, String itemDescription, int itemWeight, int itemValue, String itemCategory, int itemQuantity, String itemRarity) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
        this.itemValue = itemValue;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.itemRarity = itemRarity;
    }

    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public int getItemWeight() {
        return itemWeight;
    }
    public void setItemWeight(int itemWeight) {
        this.itemWeight = itemWeight;
    }
    public int getItemValue() {
        return itemValue;
    }
    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }
    public String getItemCategory() {
        return itemCategory;
    }
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
    public int getItemQuantity() {
        return itemQuantity;
    }
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    public String getItemRarity() {
        return itemRarity;
    }
    public void setItemRarity(String itemRarity) {
        this.itemRarity = itemRarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return itemId == that.itemId && itemWeight == that.itemWeight && itemValue == that.itemValue && itemQuantity == that.itemQuantity && Objects.equals(itemName, that.itemName) && Objects.equals(itemDescription, that.itemDescription) && Objects.equals(itemCategory, that.itemCategory) && Objects.equals(itemRarity, that.itemRarity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, itemDescription, itemWeight, itemValue, itemCategory, itemQuantity, itemRarity);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemWeight=" + itemWeight +
                ", itemValue=" + itemValue +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", itemRarity='" + itemRarity + '\'' +
                '}';
    }

    public CharSequence toStringSummary() {
        return itemWeight + " lbs" + " | " + itemQuantity + " | " + itemName;
    }
}

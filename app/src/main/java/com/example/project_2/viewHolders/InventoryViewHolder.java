package com.example.project_2.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_2.R;

public class InventoryViewHolder extends RecyclerView.ViewHolder {

    private final TextView inventoryName;
    private final TextView inventoryQuantity;
    private final TextView inventoryWeight;

    private InventoryViewHolder (View inventoryView){
        super(inventoryView);
        inventoryName = inventoryView.findViewById(R.id.inventoryItemName);
        inventoryQuantity = inventoryView.findViewById(R.id.inventoryItemQuantity);
        inventoryWeight = inventoryView.findViewById(R.id.inventoryItemWeight);
    }

    public void bind(String name, int quantity, int weight){
        inventoryName.setText(name);
        inventoryQuantity.setText(String.valueOf(quantity));
        inventoryWeight.setText(String.valueOf(weight));
    }

    static InventoryViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_list_item, parent, false);
        return new InventoryViewHolder(parent);
    }


}

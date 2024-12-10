package com.example.project_2.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.project_2.database.entities.InventoryItem;

public class InventoryAdapter extends ListAdapter<InventoryItem, InventoryViewHolder> {
    public InventoryAdapter(@NonNull DiffUtil.ItemCallback<InventoryItem> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return InventoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        InventoryItem current = getItem(position);
        holder.bind(current.getItemName(), current.getItemQuantity(), current.getItemWeight());
    }

    public static class InventoryDiff extends DiffUtil.ItemCallback<InventoryItem> {
        @Override
        public boolean areItemsTheSame(@NonNull InventoryItem oldItem, @NonNull InventoryItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull InventoryItem oldItem, @NonNull InventoryItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}

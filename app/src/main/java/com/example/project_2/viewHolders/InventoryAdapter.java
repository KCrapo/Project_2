package com.example.project_2.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_2.R;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {


    private String[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView weightTextView;
        private final TextView quantityTextView;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            nameTextView = (TextView) view.findViewById(R.id.inventoryItemName);
            weightTextView = (TextView) view.findViewById(R.id.inventoryItemWeight);
            quantityTextView = (TextView) view.findViewById(R.id.inventoryItemQuantity);
        }

        public TextView getNameTextView(){
            return nameTextView;
        }

        public TextView getWeightTextView(){
            return weightTextView;
        }

        public TextView getQuantityTextView(){
            return quantityTextView;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView
     */
    public InventoryAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inventory_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getNameTextView().setText(localDataSet[position]);
        viewHolder.getWeightTextView().setText(localDataSet[position]);
        viewHolder.getQuantityTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

}

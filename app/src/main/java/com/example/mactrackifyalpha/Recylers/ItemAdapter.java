package com.example.mactrackifyalpha.Recylers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mactrackifyalpha.Entities.ItemsEntity;
import com.example.mactrackifyalpha.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    Context context;
    ArrayList<ItemsEntity> itemsEntities;

    public ItemAdapter(Context context, ArrayList<ItemsEntity> itemsEntities) {
        this.context = context;
        this.itemsEntities = itemsEntities;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_detail_items, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ItemsEntity entity = itemsEntities.get(position);

        holder.itemName.setText(entity.getProductName());
        holder.price.setText(entity.getWarehousePrice());
        holder.quantity.setText("" + entity.getQuantity());
        holder.warehouseName.setText(entity.getWarehouseName());

    }

    @Override
    public int getItemCount() {
        return itemsEntities.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView itemName, price, quantity, warehouseName;
        CardView transactionCard;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            warehouseName = itemView.findViewById(R.id.warehouseName);
        }
    }
}

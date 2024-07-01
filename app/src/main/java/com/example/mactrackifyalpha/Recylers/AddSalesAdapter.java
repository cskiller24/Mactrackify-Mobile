package com.example.mactrackifyalpha.Recylers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mactrackifyalpha.Entities.AddDataFormEntity;
import com.example.mactrackifyalpha.Entities.AddSaleEntity;
import com.example.mactrackifyalpha.Entities.ItemsEntity;
import com.example.mactrackifyalpha.Entities.SalesEntity;
import com.example.mactrackifyalpha.R;

import java.util.ArrayList;
import java.util.Iterator;


public class AddSalesAdapter extends RecyclerView.Adapter<AddSalesAdapter.SalesViewHolder> {

    Context context;
    ArrayList<AddDataFormEntity> addDataFormEntities;
    ArrayList<String> warehouseItemsString;

    public ArrayList<AddDataFormEntity> getAddDataFormEntities() {
        return addDataFormEntities;
    }

    public AddSalesAdapter(Context context, ArrayList<AddDataFormEntity> addDataFormEntities, ArrayList<String> warehouseItemsString) {
        this.context = context;
        this.addDataFormEntities = addDataFormEntities;
        this.warehouseItemsString = warehouseItemsString;
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_sales_item, parent, false);
        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
        AddDataFormEntity sale = addDataFormEntities.get(position);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, warehouseItemsString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.products.setAdapter(adapter);
        holder.products.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addDataFormEntities.get(holder.getAdapterPosition()).setProductName(warehouseItemsString.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addDataFormEntities.get(holder.getAdapterPosition()).setQuantity(Integer.parseInt(holder.quantity.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return addDataFormEntities.size();
    }

    public static class SalesViewHolder extends RecyclerView.ViewHolder {
        EditText quantity;
        Spinner products;

        public SalesViewHolder(@NonNull View itemView) {
            super(itemView);

            products = itemView.findViewById(R.id.products);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
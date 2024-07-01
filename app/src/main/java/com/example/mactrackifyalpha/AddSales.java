package com.example.mactrackifyalpha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mactrackifyalpha.Entities.AccountEntity;
import com.example.mactrackifyalpha.Entities.AccountsEntity;
import com.example.mactrackifyalpha.Entities.AddDataFormEntity;
import com.example.mactrackifyalpha.Entities.AddSaleEntity;
import com.example.mactrackifyalpha.Entities.AddSalesPost;
import com.example.mactrackifyalpha.Entities.ItemsEntity;
import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.SalesEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Entities.WarehouseItemEntity;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.Recylers.AddSalesAdapter;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;
import com.example.mactrackifyalpha.RequestHelpers.SalesAddRequestHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class AddSales extends AppCompatActivity {
    Spinner accounts;
    RecyclerView sales;
    Button salesAdd, submit, delete;
    AddSalesAdapter addSalesAdapter;
    ArrayList<AddDataFormEntity> addDataFormEntities;
    ArrayList<WarehouseItemEntity.WarehouseItem> warehouseItems;
    ArrayList<String> warehouseItemsString;
    ArrayList<String> accountString;
    ArrayList<AccountEntity> accountEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_sales );

        addDataFormEntities = new ArrayList<>();
        addDataFormEntities.add(new AddDataFormEntity());
        accountString = new ArrayList<>();
        accountEntities = new ArrayList<>();
        warehouseItemsString = new ArrayList<>();
        warehouseItems = new ArrayList<>();

        accountString.add(" -- SELECT ACCOUNT -- ");

        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accountString);

        accounts = findViewById(R.id.Accounts);
        accounts.setAdapter(accountsAdapter);
        accounts.setSelection(0);

        addSalesAdapter = new AddSalesAdapter(this, addDataFormEntities, warehouseItemsString);
        sales = findViewById(R.id.sales);
        sales.setLayoutManager(new LinearLayoutManager(this));

        salesAdd = findViewById(R.id.salesAdd);
        salesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataFormEntities.add(new AddDataFormEntity());
                addSalesAdapter.notifyItemInserted(addDataFormEntities.size());
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = addDataFormEntities.size() - 1;
                addDataFormEntities.remove(index);
                addSalesAdapter.notifyItemRemoved(index);
            }
        });
        submit = findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accounts.getSelectedItem().toString().equals(" -- SELECT ACCOUNT -- ")) {
                    Toast.makeText(AddSales.this, "Please select account", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddSalesAdapter adapter = (AddSalesAdapter) sales.getAdapter();
                AddSalesPost post = new AddSalesPost(accounts.getSelectedItem().toString(), adapter.getAddDataFormEntities());

                    new SalesAddRequestHelper().postTransaction(
                            new Storage(getApplicationContext()).getStringPreference(Storage.AUTH_KEY, ""),
                            post,
                            new ResponseCallback<MessageEntity>() {
                                @Override
                                public void onSuccess(String message, int statusCode, MessageEntity data) {
                                    Toast.makeText(AddSales.this, "Added transaction successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddSales.this, Data.class));
                                }

                                @Override
                                public void onFailure(String error, int statusCode) {
                                    Toast.makeText(AddSales.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    Log.i("ERROR", error);
                                }

                                @Override
                                public void onValidationError(ValidationErrorResponse validationError) {
                                    Toast.makeText(AddSales.this, "" + validationError.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("VALIDATION ERROR", "" + validationError.getErrors());
                                }
                            }
                    );
            }
        });

        getData();
    }

    public void getData() {
        SalesAddRequestHelper requestHelper = new SalesAddRequestHelper();
        Storage storage = new Storage(this);
        requestHelper.getAccounts(storage.getStringPreference(Storage.AUTH_KEY, ""), new ResponseCallback<AccountsEntity>() {
            @Override
            public void onSuccess(String message, int statusCode, AccountsEntity data) {
                for(AccountEntity account : data.getData()) {
                    accountEntities.add(account);
                    accountString.add(account.getName());
                }
                addSalesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error, int statusCode) {
                Toast.makeText(AddSales.this, "Error on retrieving accounts", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {

            }
        });

        requestHelper.getWarehouseItems(storage.getStringPreference(Storage.AUTH_KEY, ""), new ResponseCallback<WarehouseItemEntity>() {
            @Override
            public void onSuccess(String message, int statusCode, WarehouseItemEntity data) {
                for(WarehouseItemEntity.WarehouseItem item : data.getData()) {
                    warehouseItems.add(item);
                    warehouseItemsString.add(item.getName());
                }
                sales.setAdapter(addSalesAdapter);
                addSalesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error, int statusCode) {
                Toast.makeText(AddSales.this, "Error on retrieving products", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {

            }
        });
    }
}
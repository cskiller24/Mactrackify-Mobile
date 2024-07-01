package com.example.mactrackifyalpha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mactrackifyalpha.Entities.ItemsEntity;
import com.example.mactrackifyalpha.Entities.TransactionEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.Recylers.ItemAdapter;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;
import com.example.mactrackifyalpha.RequestHelpers.TransactionsRequestHelper;

import java.util.ArrayList;

public class TransactionDetail extends AppCompatActivity {
    TransactionEntity transactionEntity;
    ArrayList<ItemsEntity> itemsEntities;
    TextView transactionId, accountName, status;
    ItemAdapter itemAdapter;

    RecyclerView dataRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");

        if(uuid == null) {
            Toast.makeText(this, "There is no uuid.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TransactionDetail.this, MainActivity.class));
            finish();
            return;
        }

        transactionId = findViewById(R.id.transactionId);
        accountName = findViewById(R.id.accountName);
        status = findViewById(R.id.status);
        dataRecycler = findViewById(R.id.dataRecycler);
        itemsEntities = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, itemsEntities);
        dataRecycler.setLayoutManager(new LinearLayoutManager(this));
        dataRecycler.setAdapter(itemAdapter);

        getData(uuid);
    }

    private void getData(String uuid) {
        TransactionsRequestHelper transactionsRequestHelper = new TransactionsRequestHelper();
        Storage storage = new Storage(this);

        transactionsRequestHelper.getTransaction(storage.getStringPreference(Storage.AUTH_KEY, ""), uuid, new ResponseCallback<TransactionEntity>() {
            @Override
            public void onSuccess(String message, int statusCode, TransactionEntity data) {
                transactionEntity = data;
                accountName.setText(data.getAccount().getName());
                transactionId.setText(data.getUuid());
                status.setText(data.getStatus());
                itemsEntities.addAll(data.getItemsEntities());
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error, int statusCode) {
                Toast.makeText(TransactionDetail.this, "Error on getting data", Toast.LENGTH_SHORT).show();
                Log.i("ERROR", "" + error);
                startActivity(new Intent(TransactionDetail.this, MainActivity.class));
                finish();
            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {
                Toast.makeText(TransactionDetail.this, "Error on getting data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TransactionDetail.this, MainActivity.class));
                finish();
            }
        });
    }

}
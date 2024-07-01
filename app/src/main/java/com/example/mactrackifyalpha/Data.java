package com.example.mactrackifyalpha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import com.example.mactrackifyalpha.Entities.SalesEntity;
import com.example.mactrackifyalpha.Entities.TransactionEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.Recylers.TransactionRecycler;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;
import com.example.mactrackifyalpha.RequestHelpers.TransactionsRequestHelper;

import java.util.ArrayList;

public class Data extends AppCompatActivity {
    RecyclerView dataRecycler;
    TransactionRecycler transactionRecycler;
    ArrayList<TransactionEntity> transactionEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_data );
        Button salesbutton = findViewById(R.id.AddSales);
        salesbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Data.this, AddSales.class));
            }
        } );

        transactionEntities = new ArrayList<>();
        transactionRecycler = new TransactionRecycler(this, transactionEntities);
        dataRecycler = findViewById(R.id.dataRecycler);
        dataRecycler.setAdapter(transactionRecycler);
        dataRecycler.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    private void getData() {
        TransactionsRequestHelper transactionsRequestHelper = new TransactionsRequestHelper();
        Storage storage = new Storage(this);

        transactionsRequestHelper.getTransactions(storage.getStringPreference(Storage.AUTH_KEY, ""), new ResponseCallback<ArrayList<TransactionEntity>>() {
            @Override
            public void onSuccess(String message, int statusCode, ArrayList<TransactionEntity> data) {
                transactionEntities.addAll(data);

                transactionRecycler.notifyDataSetChanged();
                Log.i("TERST", "TEST");
            }

            @Override
            public void onFailure(String error, int statusCode) {
                Log.i("TRANSACTION EROR", error);
            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {

            }
        });
    }

}
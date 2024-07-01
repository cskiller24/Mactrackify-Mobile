package com.example.mactrackifyalpha.Recylers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mactrackifyalpha.Entities.TransactionEntity;
import com.example.mactrackifyalpha.R;
import com.example.mactrackifyalpha.TransactionDetail;

import java.util.ArrayList;

public class TransactionRecycler extends RecyclerView.Adapter<TransactionRecycler.TransactionViewHolder> {

    Context context;
    ArrayList<TransactionEntity> transactionEntities;

    public TransactionRecycler(Context context, ArrayList<TransactionEntity> transactionEntities) {
        this.context = context;
        this.transactionEntities = transactionEntities;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_card, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionEntity entity = transactionEntities.get(position);
        holder.transactionCode.setText(entity.getUuid());
        holder.accountName.setText(entity.getAccount().getName());
        holder.status.setText(entity.getStatus());

        holder.transactionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionEntity entity1 = transactionEntities.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, TransactionDetail.class);
                intent.putExtra("uuid", entity1.getUuid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionEntities.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionCode, accountName, status;
        CardView transactionCard;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            transactionCard = itemView.findViewById(R.id.transactionCard);
            transactionCode = itemView.findViewById(R.id.transactionCode);
            accountName = itemView.findViewById(R.id.accountName);
            status = itemView.findViewById(R.id.status);
        }
    }
}

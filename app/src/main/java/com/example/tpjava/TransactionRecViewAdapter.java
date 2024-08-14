package com.example.tpjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionRecViewAdapter extends RecyclerView.Adapter<TransactionRecViewAdapter.ViewHolder> {

    private ArrayList<TransactionModel> transactions = new ArrayList<TransactionModel>();
    public TransactionRecViewAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_view_list, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(ArrayList<TransactionModel> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView TransactionIcon;
        private TextView transactionTitle;
        private TextView transactionAmount;
        private TextView transactionMessage;
        private TextView transactionStatus;
        private TextView transactionTimeStamp;

        public  ViewHolder(@NonNull View itemView) {
            super(itemView);
            TransactionIcon = itemView.findViewById(R.id.transactionIcon);
            transactionTitle = itemView.findViewById(R.id.transactionTitle);
            transactionAmount = itemView.findViewById(R.id.transactionAmount);
            transactionMessage = itemView.findViewById(R.id.transactionMessage);
            transactionStatus = itemView.findViewById(R.id.transactionStatus);
            transactionTimeStamp = itemView.findViewById(R.id.transactionTimeStamp);
            // initialize your views here...
        }
    }
}

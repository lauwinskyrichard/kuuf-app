package com.example.finalprojectlabmcs_2301865842;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context ctx;
    ArrayList<TransactionHistory> historyList;
    ArrayList<Product> productList;

    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void OnDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public HomeAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setList(ArrayList<TransactionHistory> historyList, ArrayList<Product> productList) {
        this.historyList = historyList;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        if (historyList != null)
        {
            int pID = historyList.get(position).getProdID();

            holder.pName.setText("P. Name: " + productList.get(pID).getName());
            holder.pTDate.setText("T. Date: " + historyList.get(position).getTransDate());
            holder.pPrice.setText("Price: " + formatRupiah.format(Double.parseDouble(String.valueOf(productList.get(pID).getPrice()))));
        }
    }

    @Override
    public int getItemCount() {
        if (historyList != null)
        {
            return historyList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView pName;
        TextView pTDate;
        TextView pPrice;
        ImageButton tDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.tvHistoryProductName);
            pTDate = itemView.findViewById(R.id.tvHistoryTransDate);
            pPrice = itemView.findViewById(R.id.tvHistoryProductPrice);
            tDelete = itemView.findViewById(R.id.HistoryDeletebtn);

            tDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            mListener.OnDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}

package com.example.finalprojectlabmcs_2301865842;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.storeViewHolder> {

    Context context;
    ArrayList<Product> productList;

    OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class storeViewHolder extends RecyclerView.ViewHolder {
        TextView storeProductName;
        TextView storeMinPlayer;
        TextView storeMaxPlayer;
        TextView storePrice;

        public storeViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);

            storeProductName = itemView.findViewById(R.id.tvStoreProductName);
            storeMinPlayer = itemView.findViewById(R.id.tvStoreMinPlayer);
            storeMaxPlayer = itemView.findViewById(R.id.tvStoreMaxPlayer);
            storePrice = itemView.findViewById(R.id.tvStoreProductPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public StoreAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public storeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        storeViewHolder svh = new storeViewHolder(v, mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(storeViewHolder holder, int position) {
        Product currentItem = productList.get(position);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.storeProductName.setText("Product Name: " + currentItem.name);
        holder.storeMinPlayer.setText("Min Player: " + String.valueOf(currentItem.minPlayer));
        holder.storeMaxPlayer.setText("Max Player: " + String.valueOf(currentItem.maxPlayer));
        holder.storePrice.setText("Price: " + formatRupiah.format(Double.parseDouble(String.valueOf(currentItem.price))) + ",-");
    }

    @Override
    public int getItemCount() {
        if (productList != null)
        {
            return productList.size();
        }
        return 0;
    }
}
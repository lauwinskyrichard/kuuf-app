package com.example.finalprojectlabmcs_2301865842;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finalprojectlabmcs_2301865842.Database.ProductHelper;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {

    RecyclerView storeRecycler;
    StoreAdapter storeAdapter;
    RecyclerView.LayoutManager storeLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        storeRecycler = findViewById(R.id.StoreRV);
        storeRecycler.setHasFixedSize(true);
        storeLayoutManager = new LinearLayoutManager(this);
        storeRecycler.setLayoutManager(storeLayoutManager);

        ProductHelper helper = new ProductHelper(getApplicationContext());
        ArrayList<Product> listProduct = helper.getAllProduct();

        storeAdapter = new StoreAdapter(getApplicationContext(), listProduct);
        storeRecycler.setAdapter(storeAdapter);
        storeAdapter.notifyDataSetChanged();

        storeAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(StoreActivity.this, ProductDetailActivity.class);
            intent.putExtra("pID", position);
            intent.putExtra("pTitle", listProduct.get(position).getName());
            intent.putExtra("pMin", listProduct.get(position).getMinPlayer());
            intent.putExtra("pMax", listProduct.get(position).getMaxPlayer());
            intent.putExtra("pPrice", listProduct.get(position).getPrice());
            intent.putExtra("pLatitude", listProduct.get(position).getLatitude());
            intent.putExtra("pLongitude", listProduct.get(position).getLongitude());
            startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.MenuHome:
                Intent intent1 = new Intent(StoreActivity.this, HomeActivity.class);
                startActivity(intent1);
                finish();
                StoreActivity.this.overridePendingTransition(0, 0);
                return true;

            case R.id.MenuProfile:
                Intent intent2 = new Intent(StoreActivity.this, ProfileActivity.class);
                startActivity(intent2);
                finish();
                StoreActivity.this.overridePendingTransition(0, 0);
                return true;

            case R.id.MenuSignout:
                Intent intent3 = new Intent(StoreActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
                StoreActivity.this.overridePendingTransition(0, 0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
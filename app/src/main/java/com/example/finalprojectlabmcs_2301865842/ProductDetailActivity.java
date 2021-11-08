package com.example.finalprojectlabmcs_2301865842;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    TextView title;
    TextView minPlayer;
    TextView maxPlayer;
    TextView price;
    ImageButton detailBack;
    Button shop;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        title = findViewById(R.id.ProductDetailTxt);
        minPlayer = findViewById(R.id.MinPlayer);
        maxPlayer = findViewById(R.id.MaxPlayer);
        price = findViewById(R.id.Price);
        detailBack = findViewById(R.id.btnDetailReturn);
        shop = findViewById(R.id.btnShop);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("pTitle"));
        minPlayer.setText(String.valueOf(intent.getIntExtra("pMin", -1)));
        maxPlayer.setText(String.valueOf(intent.getIntExtra("pMax", -1)));
        price.setText(formatRupiah.format(Double.parseDouble(String.valueOf(intent.getIntExtra("pPrice", -1)))) + ",-");

        detailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int prodID = intent.getIntExtra("pID", -1);
                int price = intent.getIntExtra("pPrice", -1);
                float latitude = Float.parseFloat(String.valueOf(intent.getDoubleExtra("pLatitude", -1.0)));
                float longitude = Float.parseFloat(String.valueOf(intent.getDoubleExtra("pLongitude", -1.0)));

                clearSavedData();
                saveData(prodID, price, latitude, longitude);

                Intent intent2 = new Intent(ProductDetailActivity.this, MapFormActivity.class);
                startActivity(intent2);
                finish();
                ProductDetailActivity.this.overridePendingTransition(0, 0);
            }
        });
    }

    private void saveData(int prodID, int price, float latitude, float longitude)
    {
        SharedPreferences shareData = getSharedPreferences("SHARED_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = shareData.edit();
        editor.putInt("ProdID", prodID);
        editor.putInt("Price", price);
        editor.putFloat("Lat", latitude);
        editor.putFloat("Long", longitude);
        editor.apply();
    }

    private void clearSavedData()
    {
        SharedPreferences shared = getSharedPreferences("SHARED_DATA", MODE_PRIVATE);
        shared.edit().clear().apply();
    }
}
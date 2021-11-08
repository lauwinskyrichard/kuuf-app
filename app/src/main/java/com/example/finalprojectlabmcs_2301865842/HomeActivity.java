package com.example.finalprojectlabmcs_2301865842;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalprojectlabmcs_2301865842.Database.HistoryHelper;
import com.example.finalprojectlabmcs_2301865842.Database.ProductHelper;
import com.example.finalprojectlabmcs_2301865842.Database.UserDataHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TextView noHis;
    TextView username;

    int smsPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (smsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        SharedPreferences getShareID = getSharedPreferences("SHARED_ID", MODE_PRIVATE);
        int userID = getShareID.getInt("UserID", -1);

        RecyclerView homeRecycler = findViewById(R.id.HistoryRV);
        homeRecycler.setHasFixedSize(true);

        HomeAdapter homeAdapter = new HomeAdapter(this);

        UserDataHelper helper = new UserDataHelper(getApplicationContext());
        ArrayList<UserData> listUser = helper.getAllUserData();

        ProductHelper helper1 = new ProductHelper(getApplicationContext());
        ArrayList<Product> listProduct = helper1.getAllProduct();

        HistoryHelper helper2 = new HistoryHelper(getApplicationContext());
        ArrayList<TransactionHistory> listHistory = helper2.getAllTransaction(userID);

        if (listHistory != null)
        {
            homeAdapter.setList(listHistory, listProduct);
            homeAdapter.notifyDataSetChanged();
        }

        homeRecycler.setAdapter(homeAdapter);
        homeRecycler.setLayoutManager(new LinearLayoutManager(this));

        noHis = findViewById(R.id.NoHistorytxt);

        if (listHistory == null)
        {
            noHis.setVisibility(View.VISIBLE);
        }
        else{
            noHis.setVisibility(View.INVISIBLE);
        }

        username = findViewById(R.id.hiTxt);
        username.setText("Hi, " + listUser.get(userID).getUsername() + "!");

        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void OnDeleteClick(int position) {
                HistoryHelper helper3 = new HistoryHelper(getApplicationContext());
                helper3.deleteTrans(position);
                listHistory.remove(position);
                homeAdapter.notifyItemRemoved(position);
                homeAdapter.notifyDataSetChanged();

                if (listHistory == null)
                {
                    noHis.setVisibility(View.VISIBLE);
                }
                else{
                    noHis.setVisibility(View.INVISIBLE);
                }

                Toast.makeText(HomeActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
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
            case R.id.MenuStore:
                Intent intent1 = new Intent(HomeActivity.this, StoreActivity.class);
                startActivity(intent1);
                finish();
                HomeActivity.this.overridePendingTransition(0, 0);
                return true;

            case R.id.MenuProfile:
                Intent intent2 = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent2);
                finish();
                HomeActivity.this.overridePendingTransition(0, 0);
                return true;

            case R.id.MenuSignout:
                Intent intent3 = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
                HomeActivity.this.overridePendingTransition(0, 0);
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
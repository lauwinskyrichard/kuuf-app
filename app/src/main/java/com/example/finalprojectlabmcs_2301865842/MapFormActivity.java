package com.example.finalprojectlabmcs_2301865842;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.finalprojectlabmcs_2301865842.Database.HistoryHelper;
import com.example.finalprojectlabmcs_2301865842.Database.UserDataHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MapFormActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;

    ImageButton btnBack;
    Button buy;

    Double latitude;
    Double longitude;

    SmsManager smsManager;
    int smsPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_form);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        smsManager = smsManager.getDefault();
        smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (smsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        btnBack = findViewById(R.id.btnMapReturn);
        buy = findViewById(R.id.btnBuy);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrame);
        fragment.getMapAsync(MapFormActivity.this);

        SharedPreferences getShareID = getSharedPreferences("SHARED_ID", MODE_PRIVATE);
        int userID = getShareID.getInt("UserID", -1);

        SharedPreferences getShareData = getSharedPreferences("SHARED_DATA", MODE_PRIVATE);
        int prodID = getShareData.getInt("ProdID", -1);
        int price = getShareData.getInt("Price", -1);
        latitude = Double.parseDouble(String.valueOf(getShareData.getFloat("Lat", -1)));
        longitude = Double.parseDouble(String.valueOf(getShareData.getFloat("Long", -1)));

        UserDataHelper helper = new UserDataHelper(getApplicationContext());
        ArrayList<UserData> listUser = helper.getAllUserData();

        int walletNow = listUser.get(userID).getWallet();

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (walletNow >= price)
                {
                    helper.updateWallet(userID + 1, walletNow - price);

                    String transDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                    HistoryHelper helper1 = new HistoryHelper(getApplicationContext());
                    helper1.insertHistory(transDate, userID, prodID);

                    Toast.makeText(MapFormActivity.this, "Transaction Successful", Toast.LENGTH_SHORT).show();

                    //isi nomor AVD
                    smsManager.sendTextMessage("5554", null, "Purchase Complete!", null, null);

                    Intent intent = new Intent(MapFormActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    MapFormActivity.this.overridePendingTransition(0, 0);
                }
                else
                {
                    Toast.makeText(MapFormActivity.this, "Your Money is Insufficient, can't Buy This Product", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        LatLng pos = new LatLng(latitude, longitude);
        gMap.addMarker(new MarkerOptions().position(pos));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(13).build();
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        gMap.getUiSettings().setScrollGesturesEnabled(false);
    }
}
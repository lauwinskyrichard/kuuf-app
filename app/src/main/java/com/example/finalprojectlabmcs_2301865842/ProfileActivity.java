package com.example.finalprojectlabmcs_2301865842;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalprojectlabmcs_2301865842.Database.UserDataHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    TextView virtualWallet;
    TextView virtualUsername;
    TextView virtualDOB;
    TextView virtualGender;
    TextView virtualPhoneNumber;

    RadioGroup topupGroup;
    RadioButton topup250;
    RadioButton topup500;
    RadioButton toppup1000;

    EditText topupConfirmPass;
    Button topup;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        virtualWallet = findViewById(R.id.VirtualWallet);
        virtualUsername = findViewById(R.id.VirtualUsername);
        virtualDOB = findViewById(R.id.VirtualDOB);
        virtualGender = findViewById(R.id.VirtualGender);
        virtualPhoneNumber = findViewById(R.id.VirtualPhoneNumber);

        topupGroup= findViewById(R.id.TopUpGroup);
        topup250 = findViewById(R.id.TopUp250);
        topup500 = findViewById(R.id.TopUp500);
        toppup1000 = findViewById(R.id.TopUp1000);

        topupConfirmPass = findViewById(R.id.TopUpConfirmPass);
        topup = findViewById(R.id.TopUpBtn);

        UserDataHelper helper = new UserDataHelper(getApplicationContext());
        ArrayList<UserData> listUser = helper.getAllUserData();

        SharedPreferences getShareID = getSharedPreferences("SHARED_ID", MODE_PRIVATE);
        int userID = getShareID.getInt("UserID", -1);

        virtualWallet.setText(formatRupiah.format(Double.parseDouble(String.valueOf(listUser.get(userID).getWallet()))) + ",-");
        virtualUsername.setText(listUser.get(userID).getUsername());
        virtualDOB.setText(listUser.get(userID).getDob());
        virtualGender.setText(listUser.get(userID).getGender());
        virtualPhoneNumber.setText(listUser.get(userID).getPhoneNumber());

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = topupConfirmPass.getText().toString();
                int walletNow = listUser.get(userID).getWallet();

                if (topupGroup.getCheckedRadioButtonId() == -1 && topupConfirmPass.getText().length() == 0)
                {
                    Toast.makeText(ProfileActivity.this, "Please Choose Nominal", Toast.LENGTH_SHORT).show();
                }
                else if (topupGroup.getCheckedRadioButtonId() != -1 && topupConfirmPass.getText().length() == 0)
                {
                    Toast.makeText(ProfileActivity.this, "Please Fill The Password to Proceed", Toast.LENGTH_SHORT).show();
                }
                else if (topupGroup.getCheckedRadioButtonId() == -1 && topupConfirmPass.getText().length() > 0)
                {
                    Toast.makeText(ProfileActivity.this, "Please Choose Nominal", Toast.LENGTH_SHORT).show();
                }
                else if (topupGroup.getCheckedRadioButtonId() != -1 && topupConfirmPass.getText().length() > 0)
                {
                    int confirm =  checkPassword(listUser, userID, password);

                    if (confirm == -1)
                    {
                        Toast.makeText(ProfileActivity.this, "Wrong Password, Please Check Again", Toast.LENGTH_SHORT).show();
                    }
                    else if (confirm == 1)
                    {
                        int selected = topupGroup.getCheckedRadioButtonId();
                        RadioButton getSelected = findViewById(selected);

                        if (getSelected.getText().toString().matches("Rp.250.000,-"))
                        {
                            UserDataHelper helper = new UserDataHelper(getApplicationContext());
                            helper.updateWallet(userID + 1, walletNow + 250000);
                            Toast.makeText(ProfileActivity.this, "Top Up Successfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            ProfileActivity.this.overridePendingTransition(0, 0);
                        }
                        else if (getSelected.getText().toString().matches("Rp.500.000,-"))
                        {
                            UserDataHelper helper = new UserDataHelper(getApplicationContext());
                            helper.updateWallet(userID + 1, walletNow + 500000);
                            Toast.makeText(ProfileActivity.this, "Top Up Successfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            ProfileActivity.this.overridePendingTransition(0, 0);
                        }
                        else if (getSelected.getText().toString().matches("Rp.1.000.000,-"))
                        {
                            UserDataHelper helper = new UserDataHelper(getApplicationContext());
                            helper.updateWallet(userID + 1, walletNow + 1000000);
                            Toast.makeText(ProfileActivity.this, "Top Up Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            ProfileActivity.this.overridePendingTransition(0, 0);
                        }
                    }
                }
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
                Intent intent1 = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent1);
                finish();
                ProfileActivity.this.overridePendingTransition(0, 0);
                return true;

            case R.id.MenuStore:
                Intent intent2 = new Intent(ProfileActivity.this, StoreActivity.class);
                startActivity(intent2);
                finish();
                ProfileActivity.this.overridePendingTransition(0, 0);
                return true;

            case R.id.MenuSignout:
                Intent intent3 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
                ProfileActivity.this.overridePendingTransition(0, 0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int checkPassword(ArrayList<UserData> listUser, int userID, String password)
    {
        if (listUser.get(userID).getPassword().matches(password))
        {
            return 1;
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
package com.example.finalprojectlabmcs_2301865842;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalprojectlabmcs_2301865842.Database.ProductHelper;
import com.example.finalprojectlabmcs_2301865842.Database.UserDataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
        Richard Lauwinsky
        2301865842
        BA03
    */

    //AVD = Pixel 3 XL API 25

    EditText loginUsername;
    TextView loginUsernameError;
    EditText loginPassword;
    TextView loginPasswordError;

    Button login;
    Button regis;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ProductHelper helper = new ProductHelper(getApplicationContext());
        ArrayList<Product> listProduct = helper.getAllProduct();
        if (listProduct == null)
        {
            mQueue = Volley.newRequestQueue(this);
            String url = "https://api.jsonbin.io/b/5eb51c6947a2266b1474d701/7";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("items");

                                for (int i=0; i<jsonArray.length(); i++)
                                {
                                    JSONObject items = jsonArray.getJSONObject(i);
                                    String name = items.getString("name");
                                    int minplayer = items.getInt("min_player");
                                    int maxplayer = items.getInt("max_player");
                                    int price = items.getInt("price");
                                    double latitude = items.getDouble("latitude");
                                    double longitude = items.getDouble("longitude");

                                    helper.insertItem(name, minplayer, maxplayer, price, latitude, longitude);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }},
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }});
            mQueue.add(request);
        }

        loginUsername = findViewById(R.id.LoginUsername);
        loginUsernameError = findViewById(R.id.LoginUsernameError);
        loginPassword = findViewById(R.id.LoginPassword);
        loginPasswordError = findViewById(R.id.LoginPasswordError);

        login = findViewById(R.id.btnLogin);
        regis = findViewById(R.id.btnRegis);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDataHelper helper = new UserDataHelper(getApplicationContext());
                ArrayList<UserData> listUser = helper.getAllUserData();

                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                if (listUser == null)
                {
                    loginUsernameError.setText("* Register First!!!");
                    loginUsernameError.setVisibility(View.VISIBLE);
                }
                else if (username.length() == 0 && password.length() == 0)
                {
                    loginUsernameError.setText("* Please Insert The Username");
                    loginUsernameError.setVisibility(View.VISIBLE);
                    loginPasswordError.setText("* Please Insert The Password");
                    loginPasswordError.setVisibility(View.VISIBLE);
                }
                else if (username.length() > 0 && password.length() == 0)
                {
                    loginUsernameError.setVisibility(View.INVISIBLE);
                    loginPasswordError.setText("* Please Insert The Password");
                    loginPasswordError.setVisibility(View.VISIBLE);
                }
                else if (username.length() == 0 && password.length() > 0)
                {
                    loginUsernameError.setText("* Please Insert The Username");
                    loginUsernameError.setVisibility(View.VISIBLE);
                    loginPasswordError.setVisibility(View.INVISIBLE);
                }
                else
                {
                    int userIndex = checkUsername(listUser, username);
                    int passIndex = checkPassword(listUser, password);
                    
                    if (userIndex == -1)
                    {
                        loginUsernameError.setText("* Username Not Available");
                        loginUsernameError.setVisibility(View.VISIBLE);
                        loginPasswordError.setVisibility(View.INVISIBLE);
                    }
                    else if (userIndex != 1 && passIndex == -1)
                    {
                        loginPasswordError.setText("* Wrong Password");
                        loginPasswordError.setVisibility(View.VISIBLE);
                        loginUsernameError.setVisibility(View.INVISIBLE);
                    }
                    else if (userIndex != -1 && passIndex != -1)
                    {
                        loginUsernameError.setVisibility(View.INVISIBLE);
                        loginPasswordError.setVisibility(View.INVISIBLE);

                        clearSavedID();
                        saveID(userIndex);

                        loginUsername.setText("");
                        loginPassword.setText("");

                        Intent intent2 = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent2);
//                        Toast.makeText(MainActivity.this, String.valueOf(userIndex), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterForm.class);
                startActivity(intent);
            }
        });
    }

    public int checkUsername(ArrayList<UserData> listUser, String username)
    {
        for (int i=0; i<listUser.size(); i++)
        {
            if (username.equals(listUser.get(i).getUsername()))
            {
                return i;
            }
        }
        return -1;
    }

    public int checkPassword(ArrayList<UserData> listUser, String password)
    {
        for (int i=0; i<listUser.size(); i++)
        {
            if (password.equals(listUser.get(i).getPassword()))
            {
                return i;
            }
        }
        return -1;
    }

    private void saveID(int id)
    {
        SharedPreferences shareID = getSharedPreferences("SHARED_ID", MODE_PRIVATE);
        SharedPreferences.Editor editor = shareID.edit();
        editor.putInt("UserID", id);
        editor.apply();
    }

    private void clearSavedID()
    {
        SharedPreferences shared = getSharedPreferences("SHARED_ID", MODE_PRIVATE);
        shared.edit().clear().apply();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Back Again to Exit", Toast.LENGTH_SHORT).show();
    }
}
package com.example.finalprojectlabmcs_2301865842;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalprojectlabmcs_2301865842.Database.UserDataHelper;

import java.util.Calendar;

public class RegisterForm extends AppCompatActivity {

    EditText regisUsername;
    TextView regisUsernameError;

    EditText regisPassword;
    TextView regisPasswordError;

    EditText regisPhoneNumber;
    TextView regisPhoneNumberError;

    EditText dob;
    DatePickerDialog picker;
    TextView regisDOBError;

    EditText regisConfirmPassword;
    TextView regisConfirmPasswordError;

    RadioGroup regisGroup;
    RadioButton regisMale;
    RadioButton regisFemale;
    TextView regisGenderError;

    CheckBox regisCheckBox;
    TextView regisCheckBoxError;

    ImageButton backToLogin;
    Button regisLogin;

    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        regisUsername = findViewById(R.id.RegisUsername);
        regisUsernameError = findViewById(R.id.RegisUsernameError);

        regisPassword = findViewById(R.id.RegisPassword);
        regisPasswordError = findViewById(R.id.RegisPasswordError);

        regisPhoneNumber = findViewById(R.id.RegisPhoneNumber);
        regisPhoneNumberError = findViewById(R.id.RegisPhoneNumberError);

        dob = findViewById(R.id.RegisDOB);
        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                picker = new DatePickerDialog(RegisterForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        regisDOBError = findViewById(R.id.RegisDOBError);

        regisConfirmPassword = findViewById(R.id.RegisConfirmPassword);
        regisConfirmPasswordError = findViewById(R.id.RegisConfirmPasswordError);

        regisGroup = findViewById(R.id.RegisGroup);
        regisMale = findViewById(R.id.RegisMale);
        regisFemale = findViewById(R.id.RegisFemale);
        regisGenderError = findViewById(R.id.RegisGenderError);

        regisCheckBox = findViewById(R.id.RegisCheckBox);
        regisCheckBoxError = findViewById(R.id.RegisCheckBoxError);

        regisLogin = findViewById(R.id.RegisLogin);
        backToLogin = findViewById(R.id.btnRegisReturn);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        regisLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int errorTemp = 0;

                String username = regisUsername.getText().toString();
                String password = regisPassword.getText().toString();
                String passwordConfirm = regisConfirmPassword.getText().toString();
                String dobData = dob.getText().toString();
                String phoneNumber = regisPhoneNumber.getText().toString();

                //Validasi Username
                if (regisUsername.getText().length() >= 6 && regisUsername.getText().length() <= 12)
                {
                    regisUsernameError.setVisibility(View.INVISIBLE);
                }
                else
                {
                    regisUsernameError.setVisibility(View.VISIBLE);
                    errorTemp++;
                }

                //Validasi Password

                if (password.length() == 0)
                {
                    regisPasswordError.setText("* Please Enter Password");
                    regisPasswordError.setVisibility(View.VISIBLE);
                    errorTemp++;
                }
                else
                {
                    if (password.length() >= 8 && isAlphanumeric(password) == true && isAlphanumericNumb(password) == true && isAlphanumericAlpha(password) == true)
                    {
                        regisPasswordError.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        regisPasswordError.setText("* More Than 8 Characters and Alphanumeric");
                        regisPasswordError.setVisibility(View.VISIBLE);

                        errorTemp++;
                    }
                }

                //Validasi Confirm Password
                if (password.length() >= 8 && isAlphanumeric(password) == true && isAlphanumericNumb(password) == true && isAlphanumericAlpha(password) == true)
                {
                    if (passwordConfirm.matches(password))
                    {
                        regisConfirmPasswordError.setVisibility(View.INVISIBLE);
                    }
                    else if (passwordConfirm.isEmpty())
                    {
                        regisConfirmPasswordError.setText("* Please Fill Confirm Password");
                        regisConfirmPasswordError.setVisibility(View.VISIBLE);
                        errorTemp++;
                    }
                    else
                    {
                        regisConfirmPasswordError.setText("* Password Not Same");
                        regisConfirmPasswordError.setVisibility(View.VISIBLE);
                        errorTemp++;
                    }
                }
                else if (password.isEmpty())
                {
                    regisConfirmPasswordError.setText("* Please Fill The Password First");
                    regisConfirmPasswordError.setVisibility(View.VISIBLE);
                }

                //Validasi DOB
                if (dobData.length() > 0)
                {
                    regisDOBError.setVisibility(View.INVISIBLE);
                }
                else
                {
                    regisDOBError.setVisibility(View.VISIBLE);
                    errorTemp++;
                }

                //Validasi Phone Numb
                if (phoneNumber.length() >= 10 && phoneNumber.length() <= 12)
                {
                    regisPhoneNumberError.setVisibility(View.INVISIBLE);
                }
                else
                {
                    regisPhoneNumberError.setVisibility(View.VISIBLE);
                    errorTemp++;
                }

                //Validasi Gender
                if (regisGroup.getCheckedRadioButtonId() == -1)
                {
                    regisGenderError.setVisibility(View.VISIBLE);
                    errorTemp++;
                }
                else
                {
                    int selected = regisGroup.getCheckedRadioButtonId();
                    RadioButton getSelected = findViewById(selected);

                    gender = getSelected.getText().toString();

                    regisGenderError.setVisibility(View.INVISIBLE);
                }

                //Validasi Check Box
                if (regisCheckBox.isChecked())
                {
                    regisCheckBoxError.setVisibility(View.INVISIBLE);
                }
                else
                {
                    regisCheckBoxError.setVisibility(View.VISIBLE);
                    errorTemp++;
                }

                if (errorTemp == 0)
                {
                    UserDataHelper helper = new UserDataHelper(getApplicationContext());
                    helper.insertUserData(username, password, phoneNumber, gender, 0, dobData);

                    finish();
                    Toast.makeText(RegisterForm.this, "Data Saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isAlphanumeric(String password)
    {
        for (int i=0; i<password.length(); i++)
        {
            char temp = password.charAt(i);
            if (!(temp>='A' && temp<='Z') && !(temp>='a' && temp<='z') && !(temp>='0' && temp<='9'))
            {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public static boolean isAlphanumericNumb(String password)
    {
        int flag = 0;
        for (int i=0; i<password.length(); i++)
        {
            char temp = password.charAt(i);
            if (temp == '1' || temp == '2' || temp == '3' || temp == '4' || temp == '5'
                    || temp == '6' || temp == '7' || temp == '8' || temp == '9')
            {
                flag = 1;
                break;
            }
        }

        if (flag == 1)
        {
            return Boolean.TRUE;
        }
        else
        {
            return Boolean.FALSE;
        }
    }

    public static boolean isAlphanumericAlpha(String password)
    {
        int flag = 0;
        for (int i=0; i<password.length(); i++)
        {
            char temp = password.charAt(i);
            if (temp == 'a' || temp == 'b' || temp == 'c' || temp == 'd' || temp == 'e' || temp == 'f' || temp == 'g' || temp == 'h' ||
                    temp == 'i' || temp == 'j' || temp == 'k' || temp == 'l' || temp == 'm' || temp == 'n' || temp == 'o' || temp == 'p' ||
                    temp == 'q' || temp == 'r' || temp == 's' || temp == 't' || temp == 'u' || temp == 'v' || temp == 'w' || temp == 'x' ||
                    temp == 'y' || temp == 'z' || temp == 'A' || temp == 'B' || temp == 'C' || temp == 'D' || temp == 'E' || temp == 'F' ||
                    temp == 'G' || temp == 'H' || temp == 'I' || temp == 'J' || temp == 'K' || temp == 'L' || temp == 'M' || temp == 'N' ||
                    temp == 'O' || temp == 'P' || temp == 'Q' || temp == 'R' || temp == 'S' || temp == 'T' || temp == 'U' || temp == 'V' ||
                    temp == 'W' || temp == 'X' || temp == 'Y' || temp == 'Z')
            {
                flag = 1;
                break;
            }
        }

        if (flag == 1)
        {
            return Boolean.TRUE;
        }
        else
        {
            return Boolean.FALSE;
        }
    }
}
package com.example.combined_task;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    ProgressBar progressBar;
    int count = 0;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar2);
        email = findViewById(R.id.txt_email2);
        password = findViewById(R.id.txt_password2);

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        if(PreferenceData.getUserLoggedInStatus(this)){
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
            finish();
        }

        //Validating EditTexts According to Required Pattern
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (s.toString().matches(getString(R.string.emailPattern)))
                {*/
                    email.setError(null);
                    /*if(!password.isEnabled())
                        password.setEnabled(true);
                }
                else if(s.length() != 0)
                {
                    email.setError("Starts with letter and followed by letters, digits, '.' and _ and then '@' character which is further followed by letters and '.'");
                }*/
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*pattern = Pattern.compile(getString(R.string.passwordPattern));
                matcher = pattern.matcher(s.toString());
                if(matcher.matches()){*/
                    password.setError(null);
                    /*if(!btn_login.isClickable())
                        btn_login.setClickable(true);
                }
                else if(s.length() != 0){
                    password.setError("Starts with Capital letter and followed with letters, digits and special characters up to length of 8 characters minimum");
                }*/
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == PERMISSION_ALL){
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            for(int i = 0; i < grantResults.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if(deniedCount == 0)
                return;
            else{
                for(Map.Entry<String, Integer> entry : permissionResults.entrySet()){
                    String permName = entry.getKey();

                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        checkAndRequestPermissions();
                    }
                }
            }
        }
    }

    public boolean checkAndRequestPermissions(){
        List<String> listPermissionsNeeded = new ArrayList<>();
        for(String perm: PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(perm);
        }
        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_ALL);
            return false;
        }
        return true;
    }

    //Buttons OnClick Functions
    public void logIn(View view){
        validate();
    }
    public void signUp(View view){
        clearFields();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clearFields() {
        email.setText("");
        password.setText("");
    }

    //Checker Function to check whether any field missing
    public void validate(){

        if(email.getText().length() == 0) {
            email.setError("Field Required");
            return;
        }
        else if(password.getText().length() == 0) {
            password.setError("Field Required");
            return;
        }
        else if(!email.getText().toString().matches(getString(R.string.emailPattern))){
            email.setError("Invalid Email");
            return;
        }
        processLogin(email, password);
    }

    //Processing login functionality
    public void processLogin(EditText email, EditText password){

        List<UserData> userList = UserData.listAll(UserData.class);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {

            progressBar.setVisibility(View.GONE);
            for(UserData userData:userList) {
                if (email.getText().toString().equals(userData.getEmailAddress()) && password.getText().toString().equals(userData.getPassword())) {
                    PreferenceData.setLoggedInUserEmail(this, userData.getEmailAddress());
                    PreferenceData.setUserLoggedInStatus(this, true);
                    count = 1;
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }
            if(count == 0)
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();

        }, 2000);
    }
}

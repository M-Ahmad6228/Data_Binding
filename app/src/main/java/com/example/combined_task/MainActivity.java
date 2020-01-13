package com.example.combined_task;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import butterknife.BindView;

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

import com.example.combined_task.databinding.ActivityMainBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Pattern pattern;
    Matcher matcher;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User();
        binding.setUser(user);

        //Validating EditTexts According to Required Pattern
        binding.txtFullname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //if (s.toString().matches(getString(R.string.namePattern))) {
                        binding.txtFullname.setError(null);
                        //if(!binding.txtEmail.isEnabled())
                          //  binding.txtEmail.setEnabled(true);
                    //} else if (s.length() != 0) {
                        //binding.txtFullname.setError("Starts with letters and followed by digits, letters, '_', '@' and '-' up to length of 6 characters minimum");
                    //}
                }
            });
        binding.txtEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //if (s.toString().matches(getString(R.string.emailPattern))) {
                        binding.txtEmail.setError(null);
                      //  if(!binding.txtPassword.isEnabled())
                        //    binding.txtPassword.setEnabled(true);
                    //} else if (s.length() != 0) {
                      //  binding.txtEmail.setError("Starts with letter and followed by letters, digits, '.' and _ and then '@' character which is further followed by letters and '.'");
                    //}
                }
            });
        binding.txtPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                   /* pattern = Pattern.compile(getString(R.string.passwordPattern));
                    matcher = pattern.matcher(s.toString());
                    if (matcher.matches()) {*/
                        binding.txtPassword.setError(null);
                        /*if(!binding.txtConfirmPassword.isEnabled())
                            binding.txtConfirmPassword.setEnabled(true);
                    } else if (s.length() != 0) {
                        binding.txtPassword.setError("Starts with Capital letter and followed with letters, digits and special characters up to length of 8 characters minimum");
                    }*/
                }
            });
        binding.txtConfirmPassword.addTextChangedListener(new TextWatcher() {
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
                    if (matcher.matches() && binding.txtPassword.getText().toString().equals(binding.txtConfirmPassword.getText().toString())) {*/
                        binding.txtConfirmPassword.setError(null);
                        /*if(validateFields() && !binding.btnSignUp.isClickable())
                            binding.btnSignUp.setClickable(true);
                    } else if (s.length() != 0) {
                        binding.txtConfirmPassword.setError("Starts with Capital and follwed with letters, digits and special characters up to length of 8 characters minimum");
                    }*/
                }
            });

        //Setting Click Listeners to Buttons
        binding.btnLogin.setOnClickListener(v -> goToLoginScreen());

        binding.btnSignUp.setOnClickListener(v -> {
            if (validateFields()) {

                binding.progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {

                    UserData userData = new UserData(binding.txtFullname.getText().toString(), binding.txtEmail.getText().toString(), binding.txtPassword.getText().toString());
                    userData.save();
                    binding.progressBar.setVisibility(View.GONE);
                    clearFields();
                    finish();

                    }, 2500);
            }
        });
    }

    //Clear All Fields
    public void clearFields(){
        binding.txtFullname.setText("");
        binding.txtEmail.setText("");
        binding.txtPassword.setText("");
        binding.txtConfirmPassword.setText("");
    }

    //Sending Intent
    public void goToLoginScreen(){
        finish();
    }

    //Checker Function if any field is Empty
    public boolean validateFields(){
        if(binding.txtFullname.getText().toString().equals("")){
            binding.txtFullname.setError("Empty Field");
            return false;
        }
        else if(binding.txtEmail.getText().toString().equals("")){
            binding.txtEmail.setError("Empty Field");
            return false;
        }
        else if(binding.txtPassword.getText().toString().equals("")){
            binding.txtPassword.setError("Empty Field");
            return false;
        }
        else if(binding.txtConfirmPassword.getText().toString().equals("")){
            binding.txtConfirmPassword.setError("Empty Field");
            return false;
        }
        else if(!binding.txtEmail.getText().toString().matches(getString(R.string.emailPattern))){
            binding.txtEmail.setError("Invalid Email");
            return false;
        }
        else if(!binding.txtPassword.getText().toString().equals(binding.txtConfirmPassword.getText().toString())){
            binding.txtConfirmPassword.setError("Passwords not matched");
            return false;
        }
        return true;
    }

}

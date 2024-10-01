package com.fit2081.fit2081_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    EditText etPasswordConfirmation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // searching for the correct button to get info from via button names
        etUsername = findViewById(R.id.editTextLoginUsername);
        etPassword = findViewById(R.id.editTextPassword);
        etPasswordConfirmation = findViewById(R.id.editTextPasswordConfirmation);
    }


    // Button captures user input and is sent for verification
    public void onRegisterUserDetailsClick(View view) {
        String usernameString = etUsername.getText().toString();
        String passwordString = etPassword.getText().toString();
        String passwordConfirmationString = etPasswordConfirmation.getText().toString();

        // sent for verification here
        verifyUserRegistration(usernameString, passwordString, passwordConfirmationString);
    }


    // Method that directs user to Login page
    public void onLoginClick(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }


    private void verifyUserRegistration(String usernameString, String passwordString, String passwordConfirmationString) {
        String message;

        // if the username isn't empty space or null
        if (!usernameString.isEmpty() && !usernameString.equals(" ")) {
            // if given passwords aren't empty space/null
            if ((!passwordString.isEmpty() && !passwordString.equals(" ")) && (!passwordConfirmationString.isEmpty() && !passwordConfirmationString.equals(" "))) {
                // if both passwords match
                if (passwordString.equals(passwordConfirmationString)) {

                    saveDataToSharedPreferences(usernameString, passwordString, passwordConfirmationString);
                    Intent intent = new Intent(this, LoginPage.class);
                    startActivity(intent);

                    message = "Registered Successfully";
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    message = "Passwords do not match. Please try again";
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                message = "Password error. Please try again";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
        else if ((usernameString.isEmpty() && usernameString.equals(" ")) || (!passwordString.isEmpty() && !passwordString.equals(" ")) && (!passwordConfirmationString.isEmpty() && !passwordConfirmationString.equals(" "))) {
            message = "Username error. Please try again";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        else {
            message = "Username & password error. Please try again";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


    // using shared preferences to save the given data
    private void saveDataToSharedPreferences(String usernameString, String passwordString, String passwordConfirmationString) {
        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

        SharedPreferences.Editor spEditor = sharedPreferences.edit();

        spEditor.putString(Keys.USERNAME_KEY, usernameString);
        spEditor.putString(Keys.PASSWORD_KEY, passwordString);
        spEditor.putString(Keys.PASSWORD_CONFIRMATION_KEY, passwordConfirmationString);

        spEditor.apply();
    }
}
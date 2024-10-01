package com.fit2081.fit2081_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    EditText etUsernameLogin;
    EditText etPasswordLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

        // Fetching the saved username from shared preferences
        String storedUsername = sharedPreferences.getString(Keys.USERNAME_KEY, "DEFAULT_USERNAME");

        // searching for the correct button to get info from via button names
        etUsernameLogin = findViewById(R.id.editTextLoginUsername);
        etUsernameLogin.setText(storedUsername); // setting the username to the stored username
        etPasswordLogin = findViewById(R.id.editTextLoginPassword);
    }


    public void onLoginClick (View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

        String usernameLoginString = etUsernameLogin.getText().toString();
        String passwordLoginString = etPasswordLogin.getText().toString();

        // fetching the user's previously saved username & password
        String storedUsername = sharedPreferences.getString(Keys.USERNAME_KEY, "DEFAULT_USERNAME");
        String storedPassword = sharedPreferences.getString(Keys.PASSWORD_KEY, "DEFAULT_PASSWORD");

        // verifying login details
        verifyUserLogin(usernameLoginString, storedUsername, passwordLoginString, storedPassword);
    }


    // redirects the user back to the main Register/Login page
    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void verifyUserLogin(String usernameLoginString, String givenUsername, String passwordLoginString, String givenPassword) {
        boolean usernameIsValid = true;
        boolean passwordIsValid = true;
        String message;

        // using boolean to check if usernames & passwords match
        if (!givenUsername.equals(usernameLoginString)) {
            usernameIsValid = false;
        }

        if (!givenPassword.equals(passwordLoginString)) {
            passwordIsValid = false;
        }

        if (usernameIsValid && passwordIsValid) {
            // Continuing on to Dashboard activity
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            message = "Login Successful";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        else {
            message = "Authentication failure: Username or Password incorrect";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
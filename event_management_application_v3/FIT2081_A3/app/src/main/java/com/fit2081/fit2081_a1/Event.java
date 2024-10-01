package com.fit2081.fit2081_a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;
import java.util.StringTokenizer;

public class Event extends AppCompatActivity {

    EditText etEventName;
    EditText etCategoryId;
    EditText etTicketsAvailable;
    Switch etEventAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        etEventName = findViewById(R.id.editTextEventName);
        etCategoryId = findViewById(R.id.editTextCategoryId);
        etTicketsAvailable = findViewById(R.id.editTextTicketsAvailable);
        etEventAvailability = findViewById(R.id.switchEventAvailability);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
    }


    public void onSaveEventClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

        String eventNameString = etEventName.getText().toString();
        String eventCategoryIdString = etCategoryId.getText().toString();
        String ticketsAvailableString = etTicketsAvailable.getText().toString();
        int ticketsAvailableInt;
        boolean eventAvailabilityBoolean = etEventAvailability.isChecked();

        String storedCategoryId = sharedPreferences.getString(Keys.CATEGORY_ID_KEY, "DEFAULT_ID");

        if (eventNameString.isEmpty()) {
            String invalidEventNameMessage = "Invalid event name. Please try again";
            Toast.makeText(this, invalidEventNameMessage, Toast.LENGTH_LONG).show();
            return;
        }
        if (ticketsAvailableString.isEmpty()) {
            ticketsAvailableInt = 0;
        }
        else {
            ticketsAvailableInt = Integer.parseInt(etTicketsAvailable.getText().toString());
        }
        if (!eventCategoryIdString.equals(storedCategoryId)) {
            String invalidCategoryIdMessage = "Invalid category ID. Please try again";
            Toast.makeText(this, invalidCategoryIdMessage, Toast.LENGTH_LONG).show();
            return;
        }
        saveEventToSharedPreferences(eventNameString, ticketsAvailableInt, eventAvailabilityBoolean);
        eventIdGenerator();
    }


    public void eventIdGenerator() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String num = "1234567890";

        Random random = new Random();
        int alphabetLength = alphabet.length();
        int numLength = num.length();

        int firstCharIndex = random.nextInt(alphabetLength);
        int secondCharIndex = random.nextInt(alphabetLength);

        int firstNumIndex = random.nextInt(numLength);
        int secondNumIndex = random.nextInt(numLength);
        int thirdNumIndex = random.nextInt(numLength);
        int fourthNumIndex = random.nextInt(numLength);

        String firstChar = String.valueOf(alphabet.charAt(firstCharIndex));
        String secondChar = String.valueOf(alphabet.charAt(secondCharIndex));

        String firstNum = String.valueOf(num.charAt(firstNumIndex));
        String secondNum = String.valueOf(num.charAt(secondNumIndex));
        String thirdNum = String.valueOf(num.charAt(thirdNumIndex));
        String fourthNum = String.valueOf(num.charAt(fourthNumIndex));

        String eventIDChar = "E" + firstChar + secondChar;
        String eventIDNum = "-" + firstNum + secondNum + thirdNum + fourthNum;

        String eventID = eventIDChar + eventIDNum;
        saveEventIDToSharedPreferences(eventID);
    }


    // saving event ID to shared preferences
    private void saveEventIDToSharedPreferences(String eventID) {
        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

        SharedPreferences.Editor spEditor = sharedPreferences.edit();

        spEditor.putString(Keys.EVENT_ID_KEY, eventID);

        spEditor.apply();

        String CategoryId = sharedPreferences.getString(Keys.CATEGORY_ID_KEY, "DEFAULT_ID");

        String message = String.format("Event saved: %s to %s ", eventID, CategoryId);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    // saving event data into shared preferences
    private void saveEventToSharedPreferences(String eventNameString, int ticketsAvailableInt, boolean eventAvailabilityBoolean) {
        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

        SharedPreferences.Editor spEditor = sharedPreferences.edit();

        spEditor.putString(Keys.EVENT_NAME_KEY, eventNameString);
        spEditor.putInt(Keys.TICKETS_NUMBER_KEY, ticketsAvailableInt);
        spEditor.putBoolean(Keys.EVENT_AVAILABILITY_KEY, eventAvailabilityBoolean);

        spEditor.apply();

    }


    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Tokenize received message here
            // Retrieve the message from the intent
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            myStringTokenizer(msg);
        }


        public void myStringTokenizer(String msg) {
            try {
                // Using StringTokenizer to parse the incoming message
                // Each piece of info is separated using a colon
                StringTokenizer sT = new StringTokenizer(msg, ";");
                SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

                String stEventName = sT.nextToken();
                String stEventCategoryId = sT.nextToken();
                String stTicketsAvailable = sT.nextToken();
                String stEventAvailability = sT.nextToken();

                etEventName.setText(stEventName);
                etCategoryId.setText(stEventCategoryId);
                etTicketsAvailable.setText(String.valueOf(stTicketsAvailable));
                etEventAvailability.setChecked(stEventAvailability.equals("TRUE"));

                String smsEventNameString = etEventName.getText().toString();
                String smsEventCategoryIdString = etCategoryId.getText().toString();
                String storedCategoryId = sharedPreferences.getString(Keys.CATEGORY_ID_KEY, "DEFAULT_ID");
                String smsTicketsAvailableString = etTicketsAvailable.getText().toString();
                int smsTicketsAvailableInt;
                String toastMessage;

                if (smsEventNameString.isEmpty()) {
                    toastMessage = "Invalid event name. Please try again";
                    Toast.makeText(Event.this, toastMessage, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!smsEventCategoryIdString.equals(storedCategoryId)) {
                    toastMessage = "Invalid category ID. Please try again";
                    Toast.makeText(Event.this, toastMessage, Toast.LENGTH_LONG).show();
                    return;
                }
                if (smsTicketsAvailableString.isEmpty()) {
                    smsTicketsAvailableInt = 0;
                } else {
                    smsTicketsAvailableInt = Integer.parseInt(etTicketsAvailable.getText().toString());
                }
            }
            catch (Exception e) {
                Toast.makeText(Event.this, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
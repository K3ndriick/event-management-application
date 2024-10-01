package com.fit2081.fit2081_a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.fit2081.fit2081_a1.provider.CategoryClass;
import com.fit2081.fit2081_a1.provider.EMAViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class EventCategory extends AppCompatActivity {

    // ASSIGNMENT 1
    EditText etCategoryName;
    EditText etCategoryEventCount;
    Switch etCategoryAvailability;

    // ASSIGNMENT 2
    CategoryClass categoryObject;
//    ArrayList<CategoryClass> listCategory = new ArrayList<>();
    static Gson GSON = new Gson();

    // ASSIGNMENT 3
    EditText etEventLocation;
    private EMAViewModel mEmaViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_category);

        // ASSIGNMENT 1
        etCategoryName = findViewById(R.id.editTextCategoryName);
        etCategoryEventCount = findViewById(R.id.editTextEventCount);
        etCategoryAvailability = findViewById(R.id.switchCategoryAvailability);

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

        // ASSIGNMENT 2
//        listCategory = addCategoryToRecyclerView(this, "category_key");

        // ASSIGNMENT 3
        etEventLocation = findViewById(R.id.editTextEventLocation);
        mEmaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
    }


    public void onSaveCategoryClick(View view) {
        String categoryNameString = etCategoryName.getText().toString();
        String categoryEventCountString = etCategoryEventCount.getText().toString();
        int finalEventCountInt;
        boolean categoryAvailabilityBoolean = etCategoryAvailability.isChecked();
        String eventLocationString = etEventLocation.getText().toString();
        String finaleventLocationString;

        // flags for boolean validation
        boolean categoryNameFlag;
        boolean categoryEventCountFlag;

        if (categoryNameValidation(categoryNameString)) {
//            String message = "Category name true REMOVE ME";
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryNameFlag = true;
        }
        else {
//            String message = "Category name false REMOVE ME";
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryNameFlag = false;
            return;
        }

        if (categoryEventCountValidation(categoryEventCountString)) {
            if (categoryEventCountString.isEmpty()) {
                finalEventCountInt = 0;
            }
            else {
                finalEventCountInt = Integer.parseInt(categoryEventCountString);
            }
//            String message = "Category event count true REMOVE ME " + finalEventCountInt;
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryEventCountFlag = true;
        }
        else {
//            String message = "Category event count false REMOVE ME";
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryEventCountFlag = false;
            return;
        }

//        if (!(eventLocationString == null || eventLocationString.isEmpty())) {
//            finaleventLocationString = eventLocationString;
//        }


        if (eventLocationString == null || eventLocationString.isEmpty()) {
            finaleventLocationString = "";
        }
        else {
            finaleventLocationString = eventLocationString;
        }


        if (categoryNameFlag && categoryEventCountFlag) {
//            String message = "BOTH TRUE";
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryIdGenerator(categoryNameString, finalEventCountInt, categoryAvailabilityBoolean, finaleventLocationString);
        }
        else {
            return;
        }
    }


    // Validating category name
    public boolean categoryNameValidation(String categoryNameString) {
        boolean categoryNameIsValid;

        if (categoryNameString.isEmpty()) {
            String message = "Invalid category name. Please try again";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryNameIsValid = false;
        }
        else if (categoryNameString.matches("[0-9]+")) {
            String message = "Invalid category name. Invalid category name examples: 1111111, Melbourne % Centre, etc";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryNameIsValid = false;
        }
        else if (categoryNameString.matches(".*[^a-zA-Z0-9\\s].*")) {
            String message = "Invalid category name. Invalid category name examples: 1111111, Melbourne % Centre, etc";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            categoryNameIsValid = false;
        }
        else {
            categoryNameIsValid = true;
        }

        return categoryNameIsValid;
    }


    // Validating category event count
    public boolean categoryEventCountValidation (String categoryEventCountString) {
        int givenCategoryEventCountInt;
        boolean categoryEventCountIsValid = false;

        if (!categoryEventCountString.isEmpty()) {
            try {
                givenCategoryEventCountInt = Integer.parseInt(categoryEventCountString);

                if (givenCategoryEventCountInt >= 0) {
                    categoryEventCountIsValid = true;
                }
                else if (givenCategoryEventCountInt < 0) {
                    String message = "Invalid event count, cannot be negative";
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    categoryEventCountIsValid = false;
                }
            }
            catch (Exception e) {
                String message = "Invalid event count, not a valid number";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                categoryEventCountIsValid = false;
            }
        }
        else {
            categoryEventCountIsValid = true;
        }

        return categoryEventCountIsValid;
    }


    // Category ID generator
    public void categoryIdGenerator(String categoryNameString, int finalEventCountInt, boolean categoryAvailabilityBoolean, String finaleventLocationString) {
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

        String categoryIDChar = "C" + firstChar + secondChar;
        String categoryIDNum = "-" + firstNum + secondNum + thirdNum + fourthNum;
        String message;

        String categoryID = categoryIDChar + categoryIDNum;

//        saveCategoryToSharedPreferences(categoryID, categoryNameString, finalEventCountInt, categoryAvailabilityBoolean);
//        saveCategorytoGsonSharedPreferences();

//        categoryObject = new CategoryClass(categoryID, categoryNameString, finalEventCountInt, categoryAvailabilityBoolean, eventLocationString);
//        listCategory.add(categoryObject);
//        saveToSharedPreferencesA2(this, "category_key", listCategory);

        categoryObject = new CategoryClass(categoryID, categoryNameString, finalEventCountInt, categoryAvailabilityBoolean, finaleventLocationString);
        mEmaViewModel.insertCategory(categoryObject);


        // Returning a toast message to indicate successful creation of category
        message = "Category saved successfully: " + categoryID;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        toDashboardActivity();
    }


    // saving category data into shared preferences
//    private void saveCategoryToSharedPreferences(String categoryID, String categoryNameString, int eventCountInt, boolean categoryAvailabilityBoolean) {
//        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);
//
//        SharedPreferences.Editor spEditor = sharedPreferences.edit();
//
//        spEditor.putString(Keys.CATEGORY_ID_KEY, categoryID);
//        spEditor.putString(Keys.CATEGORY_NAME_KEY, categoryNameString);
//        spEditor.putInt(Keys.EVENT_COUNT_KEY, eventCountInt);
//        spEditor.putBoolean(Keys.CATEGORY_AVAILABILITY_KEY, categoryAvailabilityBoolean);
//
//        spEditor.apply();
//        Toast.makeText(this, "category shared preferences", Toast.LENGTH_SHORT).show();
//
////        category = new CategoryClass(categoryID, categoryNameString, eventCountInt, categoryAvailabilityBoolean);
////        addCategoryToCategoryList(category);
//    }


//    public ArrayList<CategoryClass> addCategoryToRecyclerView(Context context, String categoryKeyString){
//        SharedPreferences categorySharedPreference = context.getSharedPreferences("show_recycler", Context.MODE_PRIVATE);
//        String json = categorySharedPreference.getString("category_key", String.valueOf(new ArrayList<CategoryClass>()));
//        Type type = new TypeToken<ArrayList<CategoryClass>>() {}.getType();
//        return GSON.fromJson(json,type);
//    }


//    public void saveToSharedPreferencesA2(Context context, String keyString, ArrayList<?> list){
//        String gsonString = GSON.toJson(list);
//        SharedPreferences.Editor editor = context.getSharedPreferences("show_recycler", Context.MODE_PRIVATE).edit();
//        editor.putString(keyString, gsonString);
//        editor.apply();
//    }


//    private void addCategoryToCategoryList(CategoryClass category) {
//        listCategory.add(category);
//
//        // INFORM ADAPTER
//        categoryRecyclerAdapter.notifyDataSetChanged();
//    }


//    private void saveCategorytoGsonSharedPreferences() {
//        String categoryArrayListString = gson.toJson(listCategory);
//
//        SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
//        edit.putString("category_key", categoryArrayListString);
//        edit.apply();
//    }


    // Directing user back to Dashboard Activity
    public void toDashboardActivity() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }










    // ASSIGNMENT 1
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

                String stCategoryName = sT.nextToken();
                String stEventCount = sT.nextToken();
                String stCategoryAvailability = sT.nextToken();

                etCategoryName.setText(stCategoryName);
                etCategoryEventCount.setText(String.valueOf(stEventCount));
                etCategoryAvailability.setChecked(stCategoryAvailability.equals("TRUE"));

                String smsCategoryNameString = etCategoryName.getText().toString();
                String smsEventCountString = etCategoryEventCount.getText().toString();
                int smsEventCountInt;
                String toastMessage;

                if (smsCategoryNameString.isEmpty()) {
                    toastMessage = "Invalid category name. Please try again";
                    Toast.makeText(EventCategory.this, toastMessage, Toast.LENGTH_LONG).show();
                    return;
                }
                if (smsEventCountString.isEmpty()) {
                    smsEventCountInt = 0;
                } else {
                    smsEventCountInt = Integer.parseInt(etCategoryEventCount.getText().toString());
                }
            }
            catch (Exception e) {
                Toast.makeText(EventCategory.this, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


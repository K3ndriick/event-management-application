package com.fit2081.fit2081_a1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.fit2081_a1.provider.CategoryClass;
import com.fit2081.fit2081_a1.provider.EMAViewModel;
import com.fit2081.fit2081_a1.provider.EventClass;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import android.view.ScaleGestureDetector;
import androidx.core.view.GestureDetectorCompat;

public class Dashboard extends AppCompatActivity {
    // ASSIGNMENT 1
    EditText etEventId;
    EditText etEventName;
    EditText etCategoryId;
    EditText etTicketsAvailable;
    Switch switchEventAvailability;

    // ASSIGNMENT 2
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    EventClass eventObject;
    LiveData<List<CategoryClass>> listCategory;
    LiveData<List<EventClass>> listEvent;
    FragmentManager fragmentManager;
    FragmentListCategory categoryFragment;
    static Gson GSON = new Gson();

    // ASSIGNMENT 3
    private EMAViewModel mEmaViewModel;
    private GestureDetectorCompat mDetector;
    TextView tvGesture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout); // Setting drawer layout as the main view activity

        etEventName = findViewById(R.id.editTextEventNameA2);
        etCategoryId = findViewById(R.id.editTextCategoryIdA2);
        etTicketsAvailable = findViewById(R.id.editTextTicketsAvailableA2);
        switchEventAvailability = findViewById(R.id.switchEventAvailabilityA2);


        // Setting up toolbar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("FIT2081_A3");


        // Setting up hamburger menu
        drawerLayout = findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Setting up navigation drawer
        navigationView = findViewById(R.id.nav_view);
        MyNavigationHandler myNavigationHandler = new MyNavigationHandler();
        navigationView.setNavigationItemSelectedListener(myNavigationHandler);


        // Setting up FAB
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyEventDetails();
//                addListItem();
//                Snackbar.make(view, "Item added to list", Snackbar.LENGTH_LONG).setAction("Undo", undoOnClickListener).show();
            }
        });

//        listCategory = addCategoryToRecyclerView(this, "category_key");
//        listEvent = addEventToRecyclerView(this, "event_key");


        // Category fragment
        fragmentManager = getSupportFragmentManager();
        categoryFragment = new FragmentListCategory();


        // ASSIGNMENT 3
        mEmaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
        listCategory = mEmaViewModel.getAllCategories();
        listEvent = mEmaViewModel.getAllEvents();

        tvGesture = findViewById(R.id.textViewGesture);

        // Setting up touchpad view for gestures
        View touchpad = findViewById(R.id.touchpad);
        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // pass stream of events to GestureDetectorCompat, which translate events into gestures
                mDetector.onTouchEvent(event);

                // get the type of Motion Event detected which is represented by a pre-defined integer value
                int action = event.getAction();

                // compare the detected event type against pre-defined values
//                if (action == MotionEvent.ACTION_DOWN){
//                    tvGesture.setText("ACTION_DOWN");
//                } else if (action == MotionEvent.ACTION_UP){
//                    tvGesture.setText("ACTION_UP");
//                } else if (action == MotionEvent.ACTION_MOVE){
//                    tvGesture.setText("ACTION_MOVE");
//                }

                return true;
            }
        });

        // initialise new instance of CustomGestureDetector class
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();

        // register GestureDetector and set listener as CustomGestureDetector
        mDetector = new GestureDetectorCompat(this, customGestureDetector);
    }



    // onCreate method for options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu); // display options_menu resource file
        return super.onCreateOptionsMenu(menu);
    }


    // Intercepting clicks from the options menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int optionsItemId = item.getItemId(); // getting the id of the clicked option

        if (optionsItemId == R.id.option_refresh) {
            onOptionRefreshClick();
        }
        else if (optionsItemId == R.id.option_clear_event_form) {
            onOptionClearEventFormClick();
        }
        else if (optionsItemId == R.id.option_delete_all_categories) {
            onOptionDeleteAllCategoriesClick();
        }
        else if (optionsItemId == R.id.option_delete_all_events) {
            onOptionDeleteAllEventsClick();
        }

        return super.onOptionsItemSelected(item);
    }


    // Intercept clicks from the navigation drawer
    class MyNavigationHandler implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int navDrawerItemId = menuItem.getItemId();

            if (navDrawerItemId == R.id.nav_menu_view_all_categories) {
                onNavMenuViewAllCategoriesClick();
            }
            else if (navDrawerItemId == R.id.nav_menu_add_category) {
                onNavMenuAddCategoryClick();
            }
            else if (navDrawerItemId == R.id.nav_menu_view_all_events) {
                onNavMenuViewAllEventsClick();
            }
            else if (navDrawerItemId == R.id.nav_menu_logout) {
                onNavMenuLogoutClick();
                finish();
            }
            return true;
        }
    }



    // Validating event details
    public void verifyEventDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);

        String eventNameString = etEventName.getText().toString();
        String eventCategoryIdString = etCategoryId.getText().toString();
        String eventTicketsAvailableString = etTicketsAvailable.getText().toString();
        int finalTicketsAvailableInt;
        boolean eventAvailabilityBoolean = switchEventAvailability.isChecked();

        boolean eventNameFlag;
        boolean eventCategoryIdFlag;
        boolean eventTicketsAvailableFlag;


        if (eventNameValidation(eventNameString)) {
            eventNameFlag = true;
        }
        else {
            eventNameFlag = false;
            return;
        }

//        if (eventCategoryIdValidation(eventCategoryIdString)) {
////            String message = "event Category ID true REMOVE ME";
////            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//            eventCategoryIdFlag = true;
//        }
//        else {
////            String message = "event Category ID false REMOVE ME";
////            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//            eventCategoryIdFlag = false;
//            return;
//        }

        if (eventTicketsAvailableValidation(eventTicketsAvailableString)) {
            if (eventTicketsAvailableString.isEmpty()) {
                finalTicketsAvailableInt = 0;
            }
            else {
                finalTicketsAvailableInt = Integer.parseInt(eventTicketsAvailableString);
            }
            eventTicketsAvailableFlag = true;
        }
        else {
            eventTicketsAvailableFlag = false;
            return;
        }



        if (eventNameFlag && eventTicketsAvailableFlag) {
            LiveData<List<CategoryClass>> itemToFindLiveData = mEmaViewModel.getCategoryId(eventCategoryIdString);

            itemToFindLiveData.observe(this, foundData -> {

                if (foundData == null || foundData.isEmpty()){
                    Toast.makeText(this, "Category does not exist, please try again", Toast.LENGTH_SHORT).show();

                    // remove observers so no further notifications are received
                    itemToFindLiveData.removeObservers(this);
                }
                else {
                    eventIdGenerator(eventNameString, eventCategoryIdString, finalTicketsAvailableInt, eventAvailabilityBoolean);

                    // remove observers so no further notifications are received
                    itemToFindLiveData.removeObservers(this);
                }
            });
        }
        else {
            return;
        }
    }


    // Validating event name
    public boolean eventNameValidation(String eventNameString) {
        boolean eventNameIsValid;

        if (eventNameString.isEmpty()) {
            String message = "Invalid event name. Please try again";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            eventNameIsValid = false;
        }
        else if (eventNameString.matches("[0-9]+")) {
            String message = "Invalid event name. Invalid event name examples: 1111111, Melbourne % Centre, etc";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            eventNameIsValid = false;
        }
        else if (eventNameString.matches(".*[^a-zA-Z0-9\\s].*")) {
            String message = "Invalid event name. Invalid event name examples: 1111111, Melbourne % Centre, etc";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            eventNameIsValid = false;
        }
        else {
            eventNameIsValid = true;
        }

        return eventNameIsValid;
    }


    // validating given Category ID
//    public boolean eventCategoryIdValidation(String givenEventCategoryIdString) {
//        boolean eventCategoryIdIsValid = false;
//
//        String storedCategoryId = mEmaViewModel.getCategoryId(givenEventCategoryIdString).toString();
//
//
//        if (storedCategoryId == null || storedCategoryId == "") {
//            eventCategoryIdIsValid = false;
//        }
//
////
////        if ((mEmaViewModel.getCategoryId(givenEventCategoryIdString).equals(givenEventCategoryIdString))) {
////            String message = "Category valid";
////            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////            eventCategoryIdIsValid = true;
////        }
//
////        if (!(givenEventCategoryIdString.equals(storedCategoryIdString))) {
////            String message = "Category does not exist, please try again";
////            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////            eventCategoryIdIsValid = false;
////        }
//        if (!eventCategoryIdIsValid) {
//            String message = "Category does not exist, please try again";
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//            eventCategoryIdIsValid = false;
//        }
//        else {
//            String message = "Category valid";
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//            eventCategoryIdIsValid = true;
//        }
//
//
////        LiveData<List<CategoryClass>> itemToFindLiveData = mEmaViewModel.getAllCategories();
//
////        itemToFindLiveData.observe(this, foundData -> {
////            for (CategoryClass categoryObject : foundData) {
////                if (givenEventCategoryIdString.equals(categoryObject.getCategoryId())) {
////                    eventCategoryIdIsValid = true;
////                    itemToFindLiveData.removeObservers(this);
////
////                    return eventCategoryIdIsValid;
////                }
////                else {
////                    String message = "Category does not exist, please try again";
////                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////
////                    eventCategoryIdIsValid = false;
////                    itemToFindLiveData.removeObservers(this);
////                    return eventCategoryIdIsValid;
////                }
////            }
////        });
////        int index = 0;
////        boolean eventCategoryIdIsValid = false;
////
////        for (String storedCategoryId : listCategory) {
////            if (givenEventCategoryIdString.equals(storedCategoryId)) {
////                eventCategoryIdIsValid = true;
////            }
////        }
////
////        if (!eventCategoryIdIsValid) {
////            String message = "Category does not exist, please try again";
////            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////        }
////        else {
////            String message = "Category valid";
////            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////        }
////
//        return eventCategoryIdIsValid;
//    }


    // Validating event ticket count
    public boolean eventTicketsAvailableValidation(String eventTicketsAvailableString) {
        int givenEventTicketsAvailableInt;
        boolean eventTicketsAvailableIsValid = false;

        if (!eventTicketsAvailableString.isEmpty()) {
            try {
                givenEventTicketsAvailableInt = Integer.parseInt(eventTicketsAvailableString);

                if (givenEventTicketsAvailableInt >= 0) {
                    eventTicketsAvailableIsValid = true;
                }
                else if (givenEventTicketsAvailableInt < 0) {
                    String message = "Invalid ticket number, cannot be negative";
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    eventTicketsAvailableIsValid = false;
                }
            }
            catch (Exception e) {
                String message = "Invalid ticket number, not a valid number";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                eventTicketsAvailableIsValid = false;
            }
        }
        else {
            eventTicketsAvailableIsValid = true;
        }

        return eventTicketsAvailableIsValid;
    }



    // Generating event ID
    public void eventIdGenerator(String eventNameString, String eventCategoryIdString, int finalTicketsAvailableInt, boolean eventAvailabilityBoolean) {
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
        int fifthNumIndex = random.nextInt(numLength);

        String firstChar = String.valueOf(alphabet.charAt(firstCharIndex));
        String secondChar = String.valueOf(alphabet.charAt(secondCharIndex));

        String firstNum = String.valueOf(num.charAt(firstNumIndex));
        String secondNum = String.valueOf(num.charAt(secondNumIndex));
        String thirdNum = String.valueOf(num.charAt(thirdNumIndex));
        String fourthNum = String.valueOf(num.charAt(fourthNumIndex));
        String fifthNum = String.valueOf(num.charAt(fifthNumIndex));

        String eventIDChar = "E" + firstChar + secondChar;
        String eventIDNum = "-" + firstNum + secondNum + thirdNum + fourthNum + fifthNum;

        String eventID = eventIDChar + eventIDNum;


//        saveEventToSharedPreferences(eventID, eventNameString, finalTicketsAvailableInt, eventAvailabilityBoolean);
//        saveEventToGsonSharedPreferences();

//        eventObject = new EventClass(eventID, eventNameString, eventCategoryIdString, finalTicketsAvailableInt, eventAvailabilityBoolean);
//        listEvent.add(eventObject);

//        categoryEventCountIncrement(eventCategoryIdString);
//        saveToSharedPreferencesA2(this, "event_key", listEvent);


        // Displaying the event ID on the form after saving
        etEventId = findViewById(R.id.editTextEventIdA2);
        etEventId.setText(eventID);


        eventObject = new EventClass(eventID, eventNameString, eventCategoryIdString, finalTicketsAvailableInt, eventAvailabilityBoolean);
        mEmaViewModel.insertEvent(eventObject);
        categoryEventCountIncrement(eventCategoryIdString);

        String message = String.format("Event saved: %s to %s ", eventID, eventCategoryIdString);
        makeSnackbar(message);
    }


    // saving event data into shared preferences
//    private void saveEventToSharedPreferences(String eventID, String eventNameString, int ticketsAvailableInt, boolean eventAvailabilityBoolean) {
//        SharedPreferences sharedPreferences = getSharedPreferences(Keys.SP_FILENAME, MODE_PRIVATE);
//
//        SharedPreferences.Editor spEditor = sharedPreferences.edit();
//
//        spEditor.putString(Keys.EVENT_ID_KEY, eventID);
//        spEditor.putString(Keys.EVENT_NAME_KEY, eventNameString);
//        spEditor.putInt(Keys.TICKETS_NUMBER_KEY, ticketsAvailableInt);
//        spEditor.putBoolean(Keys.EVENT_AVAILABILITY_KEY, eventAvailabilityBoolean);
//
//        spEditor.apply();
//
//        String CategoryId = sharedPreferences.getString(Keys.CATEGORY_ID_KEY, "DEFAULT_ID");
//
//        String message = String.format("Event saved: %s to %s ", eventID, CategoryId);
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//
//
////        addToEventList(eventID, eventNameString, CategoryId, ticketsAvailableInt, eventAvailabilityBoolean);
//    }

//    private void addToEventList(String eventID, String eventNameString, String eventCategoryID, int ticketsAvailableInt, boolean eventAvailabilityBoolean) {
//        EventClass newEvent = new EventClass(eventID, eventNameString, eventCategoryID, ticketsAvailableInt, eventAvailabilityBoolean);
//        listEvents.add(newEvent);
//    }

//    private void saveEventToGsonSharedPreferences() {
//        String eventArrayListString = GSON.toJson(listEvents);
//
//        SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
//        edit.putString("event_key", eventArrayListString);
//        edit.apply();
//    }


    public void categoryEventCountIncrement(String eventCategoryIdString) {
        LiveData<List<CategoryClass>> itemToFindLiveData = mEmaViewModel.getCategoryId(eventCategoryIdString);

        itemToFindLiveData.observe(this, foundData -> {

            if (foundData == null || foundData.isEmpty()){
//                Toast.makeText(this, "Error updating category count, please try again", Toast.LENGTH_SHORT).show();
            }
            else {
                mEmaViewModel.increaseCategoryEventCount(eventCategoryIdString);
//                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                // remove observers so no further notifications are received
                itemToFindLiveData.removeObservers(this);
            }
        });


//        for (CategoryClass categoryObject : listCategory) {
//            if (eventCategoryIdString.equals(categoryObject.getCategoryId())) {
//                categoryObject.setCategoryEventCount(categoryObject.getCategoryEventCount()+1);
//            }
//        }

//        saveToSharedPreferencesA2(this, "category_key", listCategory);
    }


    public void categoryEventCountDecrement(String eventIdToDelete, String categoryIdToDecrement) {
        LiveData<List<EventClass>> itemToFindLiveData = mEmaViewModel.getEventId(eventIdToDelete);

        itemToFindLiveData.observe(this, foundData -> {

            if (foundData == null || foundData.isEmpty()){
//                Toast.makeText(this, "Error updating category count, please try again", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                mEmaViewModel.deleteEvent(eventIdToDelete);
                mEmaViewModel.decreaseCategoryEventCount(categoryIdToDecrement);
//                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();

                // remove observers so no further notifications are received
                itemToFindLiveData.removeObservers(this);
            }
        });
    }


    public void makeSnackbar(String message) {
        Snackbar.make(drawerLayout, message, Snackbar.LENGTH_LONG).setAction("Undo", undoOnClickListener).show();
    }

    View.OnClickListener undoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String eventIdToDelete = etEventId.getText().toString();
            String categoryIdToDecrement = etCategoryId.getText().toString();

            categoryEventCountDecrement(eventIdToDelete, categoryIdToDecrement);
//            if (!listEvent.isEmpty()) {
//                String eventToDelete = etEventId.getText().toString();
//                String categoryToDelete = etCategoryId.getText().toString();
//
//                for (CategoryClass categoryObject : listCategory) {
//                    if (categoryToDelete.equals(categoryObject.getCategoryId())) {
//                        categoryObject.setCategoryEventCount(categoryObject.getCategoryEventCount()-1);
//                    }
//                }
//
//                listEvent.remove(listEvent.size() -1);
//
////                saveToSharedPreferencesA2(getApplicationContext(), "category_key", listCategory);
////                saveToSharedPreferencesA2(getApplicationContext(), "event_key", listEvent);
//            }
        }
    };


//    public ArrayList<CategoryClass> addCategoryToRecyclerView(Context context, String categoryKeyString){
//        SharedPreferences categorySharedPreference = context.getSharedPreferences("show_recycler", Context.MODE_PRIVATE);
//        String json = categorySharedPreference.getString("category_key", String.valueOf(new ArrayList<CategoryClass>()));
//        Type type = new TypeToken<ArrayList<CategoryClass>>() {}.getType();
//        return GSON.fromJson(json,type);
//    }


//    public ArrayList<EventClass> addEventToRecyclerView(Context context, String eventKeyString){
//        SharedPreferences eventSharedPreference = context.getSharedPreferences("show_recycler", Context.MODE_PRIVATE);
//        String json = eventSharedPreference.getString("event_key", String.valueOf(new ArrayList<EventClass>()));
//        Type type = new TypeToken<ArrayList<EventClass>>() {}.getType();
//        return GSON.fromJson(json,type);
//    }


//    public void saveToSharedPreferencesA2(Context context, String keyString, ArrayList<?> list){
//        String gsonString = GSON.toJson(list);
//        SharedPreferences.Editor editor = context.getSharedPreferences("show_recycler", Context.MODE_PRIVATE).edit();
//        editor.putString(keyString, gsonString);
//        editor.apply();
//    }




    // Methods for options menu options
    public void onOptionRefreshClick() {
        finish();
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void onOptionClearEventFormClick() {
        etEventId = findViewById(R.id.editTextEventIdA2);
        etEventName = findViewById(R.id.editTextEventNameA2);
        etCategoryId = findViewById(R.id.editTextCategoryIdA2);
        etTicketsAvailable = findViewById(R.id.editTextTicketsAvailableA2);
        switchEventAvailability = findViewById(R.id.switchEventAvailabilityA2);

        etEventId.setText("");
        etEventName.setText("");
        etCategoryId.setText("");
        etTicketsAvailable.setText("");
        switchEventAvailability.setChecked(false);

//        recyclerAdapter.notifyDataSetChanged();
    }

    public void onOptionDeleteAllCategoriesClick() {
//        listCategory.clear();
//        saveToSharedPreferencesA2(this, "category_key", listCategory);
        mEmaViewModel.deleteAllCategories();
    }

    public void onOptionDeleteAllEventsClick() {
//        listEvent.clear();
//        saveToSharedPreferencesA2(this, "event_key", listEvent);
        mEmaViewModel.deleteAllEvents();
    }



    // Methods for navigation drawer options
    public void onNavMenuViewAllCategoriesClick() {
        Intent intent = new Intent(this, ListCategoryActivity.class);
        startActivity(intent);
    }

    public void onNavMenuAddCategoryClick() {
        Intent intent = new Intent(this, EventCategory.class);
        startActivity(intent);
    }

    public void onNavMenuViewAllEventsClick() {
        Intent intent = new Intent(this, ListEventActivity.class);
        startActivity(intent);
    }

    public void onNavMenuLogoutClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    /**
     * Called when a touch screen event was not handled by any of the views inside of the activity.
     *
     * @param event The touch screen event being processed.
     *
     * @return must return true if event was consumed by our custom programming logic.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // get the type of Motion Event detected which is represented by a pre-defined integer value
        int action = event.getAction();

        // compare the detected event type against pre-defined values
//        if (action == MotionEvent.ACTION_DOWN){
//            tvGesture.setText("ACTION_DOWN");
//        } else if (action == MotionEvent.ACTION_UP){
//            tvGesture.setText("ACTION_UP");
//        } else if (action == MotionEvent.ACTION_MOVE){
//            tvGesture.setText("ACTION_MOVE");
//        }

        return true;
    }


    /**
     * A convenience class to extend when you only want to listen for a subset of all the gestures.
     */
    /**
     * Custom class inheriting a convenience class to extend when you only want to listen for a subset of all the gestures.
     */
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            tvGesture.setText("onLongPress");
            onOptionClearEventFormClick();

            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            tvGesture.setText("onDoubleTap");
            verifyEventDetails();

            return super.onDoubleTap(e);
        }
    }










    // ASSIGNMENT 1 CODE
    // for directing user to creating a new category
//    public void onNewEventCategoryClick (View view) {
//        Intent newEventCategoryIntent = new Intent(this, EventCategory.class);
//        startActivity(newEventCategoryIntent);
//    }

    // for directing user to creating a new event
//    public void onAddEventClick (View view) {
//        Intent newEventIntent = new Intent(this, Event.class);
//        startActivity(newEventIntent);
//    }
}
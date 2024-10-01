package com.fit2081.fit2081_a1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.fit2081.fit2081_a1.provider.CategoryClass;
import com.fit2081.fit2081_a1.provider.EMAViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.fit2081_a1.databinding.ActivityGoogleMapBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;

    private String countryToShow;
    private String categoryNameToShow;
    private SupportMapFragment supportMapFragment;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        countryToShow = getIntent().getExtras().getString("eventLocation", "Australia");
        categoryNameToShow = getIntent().getExtras().getString("eventLocationCategoryName", "Default-Category-Name");
        geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));

//        findCountryMoveCamera();

        if ((countryToShow.isEmpty() || countryToShow == null)) {
            runOnUiThread(() -> {
//                LatLng tasmania = new LatLng(-41.640079, 146.315918);
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(tasmania));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();
            });
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            /**
             * countryToShow: String value, any string we want to search
             * maxResults: how many results to return if search was successful
             * successCallback method: if results are found, this method will be executed
             *                          runs in a background thread
             */
//            LiveData<List<CategoryClass>> itemToFindLiveData = (LiveData<List<CategoryClass>>) mEmaViewModel.getEventLocation(countryToShow);
//
//            itemToFindLiveData.observe(this, foundData -> {
//
//            });

            geocoder.getFromLocationName(countryToShow, 1, addresses -> {
                // if there are results, this condition would return true
                if (!addresses.isEmpty()) {
                    // run on UI thread as the user interface will update once set map location
                    runOnUiThread(() -> {
                        // define new LatLng variable using the first address from list of addresses
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );

                        // repositions the camera according to newAddressLocation
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));

                        // just for reference add a new Marker
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(newAddressLocation)
                                        .title(categoryNameToShow)
                        );

                        // set zoom level to 8.5f or any number between range of 2.0 to 21.0
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                        Toast.makeText(this, "Location: " + countryToShow, Toast.LENGTH_SHORT).show();
                    });
                }
                else {
                    runOnUiThread(() -> {
//                        LatLng tasmania = new LatLng(-41.640079, 146.315918);
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(tasmania));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                        Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                boolean actionFlag;
                String selectedCountry = "";

                List<Address> addresses = new ArrayList<>();

                try {
                    //The results of getFromLocation are a best guess and are not guaranteed to be meaningful or correct.
                    // It may be useful to call this method from a thread separate from your primary UI thread.
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); //last param means only return the first address object
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size() == 0) {
                    String msg = "No Country at this location!! Sorry";
                    Toast.makeText(supportMapFragment.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
                else {
                    android.location.Address address = addresses.get(0);
                    selectedCountry = address.getCountryName();
                    String msg = "The selected country is " + selectedCountry;
                    Toast.makeText(supportMapFragment.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    private void findCountryMoveCamera() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//
//            /**
//             * countryToShow: String value, any string we want to search
//             * maxResults: how many results to return if search was successful
//             * successCallback method: if results are found, this method will be executed
//             *                          runs in a background thread
//             */
//            geocoder.getFromLocationName(countryToShow, 1, addresses -> {
//                // if there are results, this condition would return true
//                if (!addresses.isEmpty()) {
//                    // run on UI thread as the user interface will update once set map location
//                    runOnUiThread(() -> {
//                        // define new LatLng variable using the first address from list of addresses
//                        LatLng newAddressLocation = new LatLng(
//                                addresses.get(0).getLatitude(),
//                                addresses.get(0).getLongitude()
//                        );
//
//                        // repositions the camera according to newAddressLocation
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));
//
//                        // just for reference add a new Marker
//                        mMap.addMarker(
//                                new MarkerOptions()
//                                        .position(newAddressLocation)
//                                        .title(countryToShow)
//                        );
//
//                        // set zoom level to 8.5f or any number between range of 2.0 to 21.0
//                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
//                    });
//                }
//                else {
//                    Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();
////                    LatLng tasmania = new LatLng(-41.640079, 146.315918);
////                    mMap.addMarker(new MarkerOptions().position(tasmania).title("Marker in Tasmania"));
////                    mMap.moveCamera(CameraUpdateFactory.newLatLng(tasmania));
////                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
////                    runOnUiThread(() -> {
////                        Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();
////                    });
//                }
//            });
//        }
//    }

}
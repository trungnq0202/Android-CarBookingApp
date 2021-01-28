package com.trungngo.carshareapp.ui.driver.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trungngo.carshareapp.Constants;
import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.activities.MainActivity;
import com.trungngo.carshareapp.model.User;
import com.trungngo.carshareapp.ui.customer.booking.dropoff.DropoffFragment;
import com.trungngo.carshareapp.ui.customer.home.CustomerHomeViewModel;
import com.trungngo.carshareapp.ui.driver.alert.DriverAlertFragment;
import com.trungngo.carshareapp.ui.driver.driver_info.DriverInfoFragment;
import com.trungngo.carshareapp.ui.driver.driver_info.DriverInfoViewModel;

public class DriverHomeFragment extends Fragment implements OnMapReadyCallback {

    private DriverHomeViewModel driverHomeViewModel;

    //Google maps variables
    private static final int MY_LOCATION_REQUEST = 99;
    private SupportMapFragment supportMapFragment; //maps view
    private GoogleMap mMap;
    private FusedLocationProviderClient locationClient;
    private LocationRequest locationRequest;
    private PlacesClient placesClient;
    private Marker currentUserLocationMarker;
    private Marker currentOriginLocationMarker;
    private Marker currentDestinationLocationMarker;
    private Location currentUserLocation;


    private FloatingActionButton getMyLocationBtn;

    //Firestore instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private User currentUserObject;


    /**
     * Connect view elements for further use
     * @param rootView
     */
    private void linkViewElements(View rootView) {
        getMyLocationBtn = rootView.findViewById(R.id.fragmentMapsFindMyLocationBtn);

    }

    /**
     * Show the driver alert dialog
     */
    private void showNotifyBookingDialog() {
        FragmentManager fm = getChildFragmentManager();
        DriverAlertFragment driverAlertFragment = DriverAlertFragment.newInstance();
        driverAlertFragment.show(fm, "fragment_notify_booking");

    }

    /**
     * Request user for location permission
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_LOCATION_REQUEST);
    }

//    @SuppressLint("SetTextI18n")
//    private void setDriverInfo() {
//        driverName.setText("Username: " + currentUserObject.getUsername());
//        vehicleTypeTextView.setText("Vehicle type: " + currentUserObject.getTransportationType());
//        vehiclePlateNumberTextView.setText("Plate number: " + currentUserObject.getVehiclePlateNumber());
//        driverRating.setText(Double.toString(currentUserObject.getRating()));
//    }

    /**
     * Set Action Handlers
     */
    private void setActionHandlers() {
        setGetMyLocationBtnHandler(); //Find My location Button listener
    }

    /**
     * //Find My location Button listener
     */
    private void setGetMyLocationBtnHandler() {
        getMyLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetPositionClick();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        driverHomeViewModel =
                new ViewModelProvider(this).get(DriverHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_driver_home, container, false);
        linkViewElements(root);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        initMapsFragment();
        setActionHandlers();
        return root;
    }

    private void loadDriverInfoFragment(){
        //Load driver info fragment
        DriverInfoFragment driverInfoFragment = new DriverInfoFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.driver_info, driverInfoFragment).commit();
    }

    private void sendDriverInfoDataToViewModel(){
        DriverInfoViewModel driverInfoViewModel = ViewModelProviders.of(requireActivity()).get(DriverInfoViewModel.class);
        driverInfoViewModel.setCurrentUserObject(currentUserObject);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDriverInfoFragment();

    }



    /**
     * Init Google MapsFragment
     */
    private void initMapsFragment() {
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.fragment_maps);
        supportMapFragment.getMapAsync(this);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        driverHomeViewModel = ViewModelProviders.of(requireActivity()).get(DriverHomeViewModel.class);
        driverHomeViewModel.getCurrentUserObject().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUserObject = user;
                sendDriverInfoDataToViewModel();
            }
        });

    }


    /**
     * //Start location update listener
     */
    @SuppressLint({"MissingPermission", "RestrictedApi"})
    private void startLocationUpdate() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5 * 1000); //5s
        locationRequest.setFastestInterval(5 * 1000); //5s
        locationClient.requestLocationUpdates(locationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        Location location = locationResult.getLastLocation();
                        LatLng latLng = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        updateCurrentUserLocationMarker(latLng);
//                        updateCurrentRoute();

                    }
                }
                , null);
    }

    /**
     * Find my position action handler
     */
    @SuppressLint("MissingPermission")
    public void onGetPositionClick() {
        locationClient.getLastLocation().
                addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            Toast.makeText(getActivity(),
                                    Constants.ToastMessage.currentLocationNotUpdatedYet,
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        currentUserLocation = location;
                        if (currentUserLocationMarker == null) {
                            updateCurrentUserLocationMarker(latLng);
                        }
                        smoothlyMoveCameraToPosition(latLng, Constants.GoogleMaps.CameraZoomLevel.streets);
                    }
                });
    }

    /**
     * Update current user location marker
     * @param newLatLng
     */
    private void updateCurrentUserLocationMarker(LatLng newLatLng) {
        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        currentUserLocationMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(newLatLng)
                        .icon(bitmapDescriptorFromVector(
                                getActivity(),
                                R.drawable.ic_current_location_marker, Color.BLUE)
                        )
                        .title("You are here!")
        );
    }

    /**
     * Get BitmapDescriptor from drawable vector asset, for custom cluster marker
     * @param context
     * @param vectorResId
     * @param color
     * @return
     */
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId, int color) {
        if (context == null) {
            return null;
        }
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        DrawableCompat.setTint(vectorDrawable, color);
        DrawableCompat.setTintMode(vectorDrawable, PorterDuff.Mode.SRC_IN);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /**
     * Smoothly change camera position with zoom level
     * @param latLng
     * @param zoomLevel
     */
    private void smoothlyMoveCameraToPosition(LatLng latLng, float zoomLevel) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        String apiKey = getString(R.string.google_maps_key);
        if (!Places.isInitialized()) { //Init GooglePlaceAutocomplete if not existed
            Places.initialize(requireActivity().getApplicationContext(), apiKey);
        }
        this.placesClient = Places.createClient(requireActivity().getApplicationContext());
        mMap = googleMap;
        requestPermission(); //Request user for location permission
        locationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mMap.getUiSettings().setZoomControlsEnabled(true);
        startLocationUpdate(); //Start location update listener
//        setUpCluster(); //Set up cluster on Google Map
        onGetPositionClick();  // Position the map.
    }
}
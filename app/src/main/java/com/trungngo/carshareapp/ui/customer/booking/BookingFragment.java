package com.trungngo.carshareapp.ui.customer.booking;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.maps.android.clustering.ClusterManager;
import com.trungngo.carshareapp.Constants;
import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.model.GoogleMaps.MyClusterItem;
import com.trungngo.carshareapp.model.User;
import com.trungngo.carshareapp.ui.customer.booking.dropoff.DropoffFragment;
import com.trungngo.carshareapp.ui.customer.booking.pickup.PickupFragment;

import java.util.ArrayList;
import java.util.Objects;

public class BookingFragment extends Fragment implements OnMapReadyCallback {

    private BookingViewModel mViewModel;

    //View elements
    private FloatingActionButton getMyLocationBtn;
    private FloatingActionButton restartBookingBtn;

    //Maps marker clustering
    private ClusterManager<MyClusterItem> clusterManager;

    //Google maps variables
    private static final int MY_LOCATION_REQUEST = 99;
    private SupportMapFragment supportMapFragment; //maps view
    private GoogleMap mMap;
    private FusedLocationProviderClient locationClient;
    private LocationRequest locationRequest;
    private Marker currentUserLocationMarker;
    private LatLng prevUserLocation;
    private MyClusterItem currentTargetLocationClusterItem;
    private LatLng prevTargetLocation;
    private ArrayList<Polyline> currentRoute = new ArrayList<>();
    private PlacesClient placesClient;


    //Firebase, FireStore
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    User currentUserObject = null;

    //Booking info
    Place customerDropOffPlace;
    Place customerPickupPlace;

    public static BookingFragment newInstance() {
        return new BookingFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        linkViewElements(view); //Link view elements to class properties
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        initMapsFragment();
        setActionHandlers();

        return view;
    }

    /**
     * Connect view elements of layout to this class variable
     * @param rootView
     */
    private void linkViewElements(View rootView) {
        getMyLocationBtn = rootView.findViewById(R.id.fragmentMapsFindMyLocationBtn);
        restartBookingBtn = rootView.findViewById(R.id.fragmentMapsBackBtn);
    }

    /**
     * Set Action Handlers
     */
    private void setActionHandlers() {
        setGetMyLocationBtnHandler(); //Find My location Button listener
        setRestartBtnHandler();
    }

    private void setRestartBtnHandler(){
        restartBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to the picking drop-off place step
                loadDropOffPlacePicker();
                //Hide back btn
                restartBookingBtn.setVisibility(View.GONE);
            }
        });
    }

    private void loadDropOffPlacePicker(){
        DropoffFragment dropoffFragment = new DropoffFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.booking_info, dropoffFragment).commit();
    }

    private void loadPickupPlacePicker(){
        PickupFragment pickupFragment = new PickupFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.booking_info, pickupFragment).commit();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadDropOffPlacePicker();
    }

    /**
     * Request user for location permission
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_LOCATION_REQUEST);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(BookingViewModel.class);
        mViewModel.getCurrentUserObject().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUserObject = user;
            }
        });

        //Action handler when customer's chosen drop off place is selected
        mViewModel.getCustomerSelectedDropOffPlace().observe(requireActivity(), new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                customerDropOffPlace = place;
                restartBookingBtn.setVisibility(View.VISIBLE); //Show back button

                //TODO Move to customerPickUpPlace fragment
                loadPickupPlacePicker();
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
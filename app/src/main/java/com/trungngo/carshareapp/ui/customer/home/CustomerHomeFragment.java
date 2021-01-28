package com.trungngo.carshareapp.ui.customer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trungngo.carshareapp.Constants;
import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.model.User;
import com.trungngo.carshareapp.ui.customer.booking.BookingViewModel;
import com.trungngo.carshareapp.ui.customer.booking.checkout.CheckoutViewModel;

import java.util.Objects;

public class CustomerHomeFragment extends Fragment {

    private CustomerHomeViewModel customerHomeViewModel;

    private ImageButton bikeBtn;
    private ImageButton carBtn;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private User currentUserObject;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        linkViewElements(view); //Link view elements to class properties
        setActionHandlers();


        return view;
    }


    private void linkViewElements(View rootView){
        bikeBtn = rootView.findViewById(R.id.bike_image_button);
        carBtn = rootView.findViewById(R.id.car_image_button);
    }

    private void setActionHandlers(){
        setBikeBtnActionHandler();
        setCarBtnActionHandler();
    }

    private void setBikeBtnActionHandler(){
        bikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass data of chosen transportation type
                CheckoutViewModel checkoutViewModel = ViewModelProviders.of(requireActivity()).get(CheckoutViewModel.class);
                checkoutViewModel.setTransportationType(Constants.Transportation.Type.bikeType);

                BookingViewModel bookingViewModel = ViewModelProviders.of(requireActivity()).get(BookingViewModel.class);
                bookingViewModel.setTransportationType(Constants.Transportation.Type.bikeType);

                //Move to Drop off page
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_customer_booking);
            }
        });
    }

    private void setCarBtnActionHandler(){
        carBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass data of chosen transportation type
                CheckoutViewModel checkoutViewModel = ViewModelProviders.of(requireActivity()).get(CheckoutViewModel.class);
                checkoutViewModel.setTransportationType(Constants.Transportation.Type.carType);

                BookingViewModel bookingViewModel = ViewModelProviders.of(requireActivity()).get(BookingViewModel.class);
                bookingViewModel.setTransportationType(Constants.Transportation.Type.carType);

                //Move to Drop off page
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_customer_booking);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customerHomeViewModel = ViewModelProviders.of(this).get(CustomerHomeViewModel.class);
        customerHomeViewModel.getCurrentUserObject().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUserObject = user;
            }
        });
    }
}
package com.trungngo.carshareapp.ui.customer.booking.popup_driver_info;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.model.User;

public class PopupDriverInfoFragment extends DialogFragment {

    private PopupDriverInfoViewModel mViewModel;

    private TextView driverUsernameTextView;
    private TextView plateNumberAndBike;
    private RatingBar ratingBar;

    private User driver;

    public static PopupDriverInfoFragment newInstance() {
        return new PopupDriverInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup_driver_info, container, false);
        linkViewElements(view);


        return view;
    }

    private void linkViewElements(View rootView){
        driverUsernameTextView = rootView.findViewById(R.id.driverUsernameTextView);
        plateNumberAndBike = rootView.findViewById(R.id.plateNumberAndBike);
        ratingBar = rootView.findViewById(R.id.ratingBar);
    }


    @SuppressLint("SetTextI18n")
    private void setDriverInfo(){
        driverUsernameTextView.setText(driver.getUsername());
        plateNumberAndBike.setText(driver.getVehiclePlateNumber() + " ● " + driver.getTransportationType());
        ratingBar.setRating(getRatingAverage(driver));
    }

    public float getRatingAverage(User driver) {
        double total = 0;
        for (int _rating : driver.getRating()) {
            total += _rating;
        }
        return (float) (total / driver.getRating().size());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(PopupDriverInfoViewModel.class);
        mViewModel.getDriver().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                driver = user;
                setDriverInfo();
            }
        });

    }

}
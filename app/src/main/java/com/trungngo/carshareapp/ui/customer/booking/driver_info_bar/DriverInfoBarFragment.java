package com.trungngo.carshareapp.ui.customer.booking.driver_info_bar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

public class DriverInfoBarFragment extends Fragment {

    private DriverInfoBarViewModel mViewModel;

    private TextView driverUsernameTextView;
    private TextView plateNumberTextView;
    private TextView transportationTypeTextView;
    private RatingBar ratingBar;

    public static DriverInfoBarFragment newInstance() {
        return new DriverInfoBarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_info_bar, container, false);
        linkViewElement(view);


        return view;
    }

    private void linkViewElement(View rootView){
        driverUsernameTextView = rootView.findViewById(R.id.driverUsernameTextView);
        plateNumberTextView = rootView.findViewById(R.id.plateNumberTextView);
        transportationTypeTextView = rootView.findViewById(R.id.transportationTypeTextView);
        ratingBar = rootView.findViewById(R.id.score_rating_bar);

    }

    private void setDriverInfo(User driver) {
        driverUsernameTextView.setText(driver.getUsername());
        plateNumberTextView.setText(driver.getVehiclePlateNumber());
        transportationTypeTextView.setText(driver.getTransportationType());
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
        mViewModel =  ViewModelProviders.of(requireActivity()).get(DriverInfoBarViewModel.class);
        mViewModel.getDriver().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                setDriverInfo(user);
            }
        });
    }

}
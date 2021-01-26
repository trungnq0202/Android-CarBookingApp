package com.trungngo.carshareapp.ui.customer.booking.rating;

import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.ui.driver.booking.DriverBookingFragment;


public class customerRatingFragment extends Fragment {
    public static customerRatingFragment newInstance() {
        return new customerRatingFragment();
    }

    private TextView driverNameText;
    private RatingBar ratingBar;
    private Button submitRatingBtn;
    private Float rating;

    private void linkViewElements(View rootView) {
        driverNameText = rootView.findViewById(R.id.text_driver_name);
        ratingBar = rootView.findViewById(R.id.ratingBar);
        submitRatingBtn = rootView.findViewById(R.id.btn_submitRating);
    }


    private void addEventListenerForSubmitBtn() {
        submitRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = ratingBar.getRating();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_rating, container, false);
        linkViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

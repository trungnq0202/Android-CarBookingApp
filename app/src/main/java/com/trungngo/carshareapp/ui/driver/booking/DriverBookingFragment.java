package com.trungngo.carshareapp.ui.driver.booking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.ui.driver.alert.DriverAlertFragment;
import com.trungngo.carshareapp.ui.driver.checkout.CheckoutFragment;

public class DriverBookingFragment extends Fragment {
    public static final String TAG = "driverBookingFragment";

    private DriverBookingViewModel mViewModel;

    enum BookingState {
        CONFIRM,
        PICKUP,
        DROPOFF,
        CHECKOUT
    }

    BookingState state;


    private TextView pickUpText;
    private TextView dropOffText;
    private TextView addressText;
    private TextView serviceText;
    private TextView paymentMethodsText;

    private Button directionBtn;
    private Button callBtn;
    private Button messageBtn;
    private Button pickUpBtn;



    public static DriverBookingFragment newInstance() {
        return new DriverBookingFragment();
    }

    private void linkViewElements(View rootView) {
        pickUpText = rootView.findViewById(R.id.text_pickup);
        dropOffText = rootView.findViewById(R.id.text_dropOff);
        addressText = rootView.findViewById(R.id.text_address);
        serviceText = rootView.findViewById(R.id.text_service);
        paymentMethodsText = rootView.findViewById(R.id.text_payment_method);

        directionBtn = rootView.findViewById(R.id.btn_direction);
        callBtn = rootView.findViewById(R.id.btn_call);
        messageBtn = rootView.findViewById(R.id.btn_message);
        pickUpBtn = rootView.findViewById(R.id.btn_pickUp);
    }

    // CONFIRM STATE
    private void setViewInConfirmState() {
        pickUpBtn.setText(R.string.btn_confirm_state);
        pickUpText.setBackgroundColor(getResources().getColor(R.color.darker_gray));
        dropOffText.setBackgroundColor(getResources().getColor(R.color.light_gray));
    }

    // PICKUP STATE
    private void setViewInPickupState() {
        pickUpBtn.setText(R.string.btn_pickup_state);
    }

    //DROP OFF STATE
    private void setViewInDropOffState() {
        pickUpBtn.setText(R.string.btn_dropoff_state);
        pickUpText.setBackgroundColor(getResources().getColor(R.color.light_gray));
        dropOffText.setBackgroundColor(getResources().getColor(R.color.darker_gray));
    }

    private void setViewInCheckoutState() {
        pickUpBtn.setText(R.string.btn_pickup_state);
    }

    private void showEditDialog() {
        FragmentManager fm = getChildFragmentManager();
        CheckoutFragment checkoutFragment = CheckoutFragment.newInstance();
        checkoutFragment.show(fm, "fragment_driver_checkout");
    }

    private void addEventListenerForPickUpButton() {
        pickUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {
                    case CONFIRM:
                        setViewInConfirmState();
                        state = BookingState.PICKUP;
                        break;
                    case PICKUP:
                        setViewInPickupState();
                        state = BookingState.DROPOFF;
                        break;
                    case DROPOFF:
                        setViewInDropOffState();
                        state = BookingState.CHECKOUT;
                        break;
                    case CHECKOUT:
                        state = BookingState.CONFIRM;
                        setViewInConfirmState();
                        showEditDialog();
                        break;
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_recieve_booking, container, false);
        linkViewElements(view);
        state = BookingState.PICKUP;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DriverBookingViewModel.class);
        // TODO: Use the ViewModel
        addEventListenerForPickUpButton();
    }

}

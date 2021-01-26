package com.trungngo.carshareapp.ui.driver.alert;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.ui.driver.booking.DriverBookingFragment;

public class DriverAlertFragment extends DialogFragment {
    public static String TAG = "DriverAlertDialog";

    private TextView priceText;
    private TextView paymentTypeText;
    private TextView serviceText;
    private TextView distanceText;
    private TextView pickUpLocationText;
    private TextView dropOffLocationText;

    private Button declineBtn;
    private Button acceptBtn;

    public static DriverAlertFragment newInstance() {
        return new DriverAlertFragment();
    }


    private void linkViewElements(View rootView) {
        priceText = rootView.findViewById(R.id.text_price);
        paymentTypeText = rootView.findViewById(R.id.text_payment_method);
        serviceText = rootView.findViewById(R.id.text_service);
        distanceText = rootView.findViewById(R.id.text_distance);
        pickUpLocationText = rootView.findViewById(R.id.text_pickUpLocation);
        pickUpLocationText = rootView.findViewById(R.id.text_dropLocation);
        declineBtn = rootView.findViewById(R.id.btn_decline);
        acceptBtn = rootView.findViewById(R.id.btn_accept);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_DeviceDefault_Dialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_driver_alert, container, false);
        linkViewElements(root);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DriverBookingFragment driverBookingFragment = new DriverBookingFragment();
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment, driverBookingFragment).commit();
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_driver_booking);

            }
        });
    }
}

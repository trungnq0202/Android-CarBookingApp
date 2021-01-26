package com.trungngo.carshareapp.ui.driver.alert;

import android.app.Dialog;
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

import com.trungngo.carshareapp.R;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_driver_alert, container, false);
        linkViewElements(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

package com.trungngo.carshareapp.ui.driver.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.ui.customer.home.CustomerHomeViewModel;
import com.trungngo.carshareapp.ui.driver.alert.DriverAlertFragment;

public class DriverHomeFragment extends Fragment {

    private DriverHomeViewModel driverHomeViewModel;

    private TextView driverName;
    private TextView driverCompletedDrives;
    private TextView driverCompletedRatio;
    private TextView driverRating;
    private Button btnActive;


    /**
     * Connect view elements for further use
     * @param rootView
     */
    private void linkViewElements(View rootView) {
        driverName = rootView.findViewById(R.id.text_name);
        driverCompletedDrives = rootView.findViewById(R.id.text_completed);
        driverCompletedRatio = rootView.findViewById(R.id.text_completed_ratio);
        driverRating = rootView.findViewById(R.id.text_rating);
        btnActive = rootView.findViewById(R.id.btn_active);
    }

    /**
     * Show the driver alert dialog
     */
    private void showEditDialog() {
        FragmentManager fm = getChildFragmentManager();
        DriverAlertFragment driverAlertFragment = DriverAlertFragment.newInstance();
        driverAlertFragment.show(fm, "fragment_notify_booking");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        driverHomeViewModel =
                new ViewModelProvider(this).get(DriverHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_driver_home, container, false);
        linkViewElements(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
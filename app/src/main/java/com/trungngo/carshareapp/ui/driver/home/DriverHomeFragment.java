package com.trungngo.carshareapp.ui.driver.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.ui.customer.home.CustomerHomeViewModel;

public class DriverHomeFragment extends Fragment {

    private DriverHomeViewModel driverHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        driverHomeViewModel =
                new ViewModelProvider(this).get(DriverHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_driver_home, container, false);

        return root;
    }
}
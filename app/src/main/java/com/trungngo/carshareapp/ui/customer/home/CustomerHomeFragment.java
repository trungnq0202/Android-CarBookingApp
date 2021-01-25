package com.trungngo.carshareapp.ui.customer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.trungngo.carshareapp.R;

public class CustomerHomeFragment extends Fragment {

    private CustomerHomeViewModel customerHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerHomeViewModel =
                new ViewModelProvider(this).get(CustomerHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_customer_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        return root;
    }
}
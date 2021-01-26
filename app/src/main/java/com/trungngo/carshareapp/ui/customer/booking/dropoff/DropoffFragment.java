package com.trungngo.carshareapp.ui.customer.booking.dropoff;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trungngo.carshareapp.R;

public class DropoffFragment extends Fragment {

    private DropoffViewModel mViewModel;

    public static DropoffFragment newInstance() {
        return new DropoffFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dropoff, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DropoffViewModel.class);
        // TODO: Use the ViewModel
    }

}
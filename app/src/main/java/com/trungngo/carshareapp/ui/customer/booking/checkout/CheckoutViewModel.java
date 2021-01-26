package com.trungngo.carshareapp.ui.customer.booking.checkout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trungngo.carshareapp.model.User;

public class CheckoutViewModel extends ViewModel {
    private MutableLiveData<User> currentUserObject;
    private MutableLiveData<String> transportationType;

    public CheckoutViewModel() {
        currentUserObject = new MutableLiveData<>();
        transportationType = new MutableLiveData<>();
    }

    public void setCurrentUserObject(User currentUserObject) {
        this.currentUserObject.setValue(currentUserObject);
    }

    public void setTransportationType(String transportationType) {
        this.transportationType.setValue(transportationType);
    }

    public MutableLiveData<User> getCurrentUserObject(){
        return this.currentUserObject;
    }

    public MutableLiveData<String> getTransportationType() {
        return transportationType;
    }
}
package com.trungngo.carshareapp.ui.driver.process_booking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trungngo.carshareapp.model.User;

public class DriverProcessBookingViewModel extends ViewModel {
    private MutableLiveData<User> currentUserObject;


    public DriverProcessBookingViewModel() {
        currentUserObject = new MutableLiveData<>();
    }

    public void setCurrentUserObject(User currentUserObject) {
        this.currentUserObject.setValue(currentUserObject);
    }

    public MutableLiveData<User> getCurrentUserObject(){
        return this.currentUserObject;
    }
}

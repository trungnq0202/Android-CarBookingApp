package com.trungngo.carshareapp.ui.driver.booking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trungngo.carshareapp.model.User;

public class DriverBookingViewModel extends ViewModel {
    private MutableLiveData<User> currentUserObject;


    public DriverBookingViewModel() {
        currentUserObject = new MutableLiveData<>();
    }

    public void setCurrentUserObject(User currentUserObject) {
        this.currentUserObject.setValue(currentUserObject);
    }

    public MutableLiveData<User> getCurrentUserObject(){
        return this.currentUserObject;
    }
}

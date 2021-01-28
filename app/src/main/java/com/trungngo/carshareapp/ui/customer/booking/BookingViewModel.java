package com.trungngo.carshareapp.ui.customer.booking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trungngo.carshareapp.model.User;

public class BookingViewModel extends ViewModel {
    private MutableLiveData<User> currentUserObject;

    public BookingViewModel() {
        currentUserObject = new MutableLiveData<>();
    }

    public void setCurrentUserObject(User currentUserObject) {
        this.currentUserObject.setValue(currentUserObject);
    }

    public MutableLiveData<User> getCurrentUserObject(){
        return this.currentUserObject;
    }
}

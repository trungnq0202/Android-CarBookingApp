package com.trungngo.carshareapp.ui.customer.booking.dropoff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trungngo.carshareapp.model.User;

public class DropoffViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<User> currentUserObject;


    public DropoffViewModel() {
        currentUserObject = new MutableLiveData<>();
    }

    public void setCurrentUserObject(User currentUserObject) {
        this.currentUserObject.setValue(currentUserObject);
    }

    public MutableLiveData<User> getCurrentUserObject(){
        return this.currentUserObject;
    }
}

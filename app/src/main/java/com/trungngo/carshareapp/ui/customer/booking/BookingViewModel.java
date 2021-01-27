package com.trungngo.carshareapp.ui.customer.booking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.libraries.places.api.model.Place;
import com.trungngo.carshareapp.model.User;

public class BookingViewModel extends ViewModel {
    private MutableLiveData<User> currentUserObject;
    private MutableLiveData<Place> customerSelectedDropOffPlace;
    private MutableLiveData<Place> customerSelectedPickupPlace;


    public BookingViewModel() {
        currentUserObject = new MutableLiveData<>();
        customerSelectedDropOffPlace = new MutableLiveData<>();
        customerSelectedPickupPlace = new MutableLiveData<>();
    }

    public void setCurrentUserObject(User currentUserObject) {
        this.currentUserObject.setValue(currentUserObject);
    }

    public void setCustomerSelectedDropOffPlace(Place customerSelectedDropOffPlace) {
        this.customerSelectedDropOffPlace.setValue(customerSelectedDropOffPlace);
    }

    public void setCustomerSelectedPickupPlace(Place customerSelectedPickupPlace) {
        this.customerSelectedPickupPlace.setValue(customerSelectedPickupPlace);
    }

    public MutableLiveData<Place> getCustomerSelectedPickupPlace() {
        return customerSelectedPickupPlace;
    }

    public MutableLiveData<User> getCurrentUserObject(){
        return this.currentUserObject;
    }

    public MutableLiveData<Place> getCustomerSelectedDropOffPlace() {
        return customerSelectedDropOffPlace;
    }
}

package com.trungngo.carshareapp.ui.driver.alert;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trungngo.carshareapp.model.Booking;

public class DriverAlertViewModel extends ViewModel {
    private MutableLiveData<Booking> booking;

    public DriverAlertViewModel() {
        booking = new MutableLiveData<>();
    }

    public void setBooking(Booking booking) {
        this.booking.setValue(booking);
    }

    public MutableLiveData<Booking> getBooking() {
        return booking;
    }
}

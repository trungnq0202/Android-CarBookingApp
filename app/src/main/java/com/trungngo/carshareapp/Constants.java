package com.trungngo.carshareapp;

public class Constants {
    public static class Transportation{
        public static class Type {
            public static final String carType = "car";
            public static final String bikeType = "bike";
        }

        public static class UnitPrice {
            public static final double carType = 5000.0;
            public static final double bikeType = 3000.0;
        }
    }

    //Fields of FireStore 'users' collection
    public static class FSUser {
        public static final String userCollection = "users";
        public static final String usernameField = "username";
        public static final String phoneField = "phone";
        public static final String birthDateField = "birthDate";
        public static final String genderField = "gender";
        public static final String emailField = "email";
        public static final String roleField = "role";
        public static final String transportationType = "transportationType";
        public static final String vehiclePlateNumber = "vehiclePlateNumber";
        public static final String rating = "rating";
        public static final String currentPositionLatitude = "currentPositionLatitude";
        public static final String currentPositionLongitude = "currentPositionLongitude";


        public static final String roleCustomerVal = "Customer";
        public static final String roleDriverVal = "Driver";
    }

    public static class FSBooking {
        public static final String bookingCollection = "bookings";
        public static final String pickupPlaceAddress = "pickupPlaceAddress";
        public static final String dropOffPlaceAddress = "dropOffPlaceAddress";
        public static final String pickUpPlaceLatitude = "pickUpPlaceLatitude";
        public static final String pickUpPlaceLongitude = "pickUpPlaceLongitude";
        public static final String dropOffPlaceLatitude = "dropOffPlaceLatitude";
        public static final String dropOffPlaceLongitude = "dropOffPlaceLongitude";

        public static final String driver = "driver";
        public static final String distanceInKm = "distanceInKm";
        public static final String priceInVND = "priceInVND";
        public static final String transportationType = "transportationType";
        public static final String available = "available";
        public static final String arrived = "arrived";
        public static final String finished = "finished";

    }



    //All Toast messages being used
    public static class ToastMessage {
        public static final String emptyInputError = "Please fill in your account authentication.";
        public static final String signInSuccess = "Sign in successfully!";
        public static final String signInFailure = "Invalid email/password!";
        public static final String registerSuccess = "Successfully registered";
        public static final String registerFailure = "Authentication failed, email must be unique and has correct form!";
        public static final String retrieveUsersInfoFailure = "Error querying for all users' information!";
        public static final String emptyMessageInputError = "Please type your message to send!";

        //Create site validation message

        public static final String placeAutocompleteError = "Google PlaceAutocomplete error with code: ";


        //Maps Error Handling
        public static final String currentLocationNotUpdatedYet = "Please wait for a few seconds for current location to be updated!";
        public static final String routeRenderingInProgress = "Please wait, the route is being rendered!";

        //Edit site Message
        public static final String editSiteSuccess = "Edit site successfully!";

        //Booking error
        public static final String addNewBookingToDbFail = "Fail to create new booking";

    }

    public static class PlaceAddressComponentTypes {
        public static final String premise = "premise";
        public static final String streetNumber = "street_number";
        public static final String route = "route";
        public static final String adminAreaLv1 = "administrative_area_level_1";
        public static final String adminAreaLv2 = "administrative_area_level_2";
        public static final String country = "country";
    }

    public static class MenuItemsIndex {
        public static final int myCreatedSitesItemIndex = 0;
        public static final int joinSitesItemIndex = 1;
        public static final int createSiteItemIndex = 2;
    }

    public static class GoogleMaps {
        public static class CameraZoomLevel {
            public static final int city = 10;
            public static final int streets = 15;
            public static final int buildings = 20;

            public static final float betweenCityAndStreets = (float) 12.5;
            public static final float betweenStreetsAndBuildings = (float) 17.5;

        }

        public static class DirectionApi {
            public static final String baseUrl = "https://maps.googleapis.com/maps/api/directions/";
            public static final String originParam = "origin";
            public static final String destinationParam = "destination";
            public static final String modeParam = "mode";
            public static final String outputParam = "json";
        }
    }

    public static class Notification{
        public static String CHANNEL_ID = "GAC";
        public static String CHANNEL_NAME = "GreenAndClean notification";
        public static String CHANNEL_DES = "GreenAndClean app notification";
        public static String title = "Green&Clean notification";
        public static String onSiteChangeTextContent = "There has been some changes made to one of your participating site, click to see...";
    }
}

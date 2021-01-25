package com.trungngo.carshareapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trungngo.carshareapp.Constants;
import com.trungngo.carshareapp.R;
import com.trungngo.carshareapp.model.User;
import com.trungngo.carshareapp.ui.customer_home.CustomerHomeViewModel;
import com.trungngo.carshareapp.ui.driver_home.DriverHomeViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private TextView navHeaderEmailTextView;
    private TextView navHeaderUsernameTextView;

    //Firebase, FireStore
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    //Current user info
    User currentUserObject = null;

    //View models
    CustomerHomeViewModel customerHomeViewModel;
    DriverHomeViewModel driverHomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //setup navigation drawer
        linkViewElements(); //Get view elements
        initAllChildFragmentsViewModel(); //Init all child fragments viewModels
        initFirebaseCurrentUserInfo(); //Get all fireStore instances
    }

    /**
     * Connect view elements of layout to this class variable
     */
    private void linkViewElements() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        LinearLayout navHeaderView = (LinearLayout) navigationView.getHeaderView(0);
        navHeaderUsernameTextView = (TextView) navHeaderView.getChildAt(1);
        navHeaderEmailTextView = (TextView) navHeaderView.getChildAt(2);
    }

    /**
     * Set up navigation drawer activity
     */
    private void navigationDrawerSetup() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //If this user is customer
        if (currentUserObject.isCustomer()) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_customer_home)
                    .setDrawerLayout(drawer)
                    .build();
        } else { //If this user is driver
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_driver_home)
                    .setDrawerLayout(drawer)
                    .build();
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigateAndHideAccordingMenuBasedOnRole(navController);
    }

    /**
     * Get instances of Firebase FireStore Auth, db, current user
     */
    private void initFirebaseCurrentUserInfo() {
        //Get instances of Firebase FireStore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        getCurrentUserObject(); //Get current user object info
    }

    /**
     * Get current user object from FireStore
     */
    private void getCurrentUserObject() {
        db.collection(Constants.FSUser.userCollection)
                .whereEqualTo(Constants.FSUser.emailField, currentUser.getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (QueryDocumentSnapshot doc : value) {
                            currentUserObject = doc.toObject(User.class);
                            setNavHeaderEmailAndUsername(); //Set nav header username and email
                            setAllChildFragmentsViewModelData();
                            navigationDrawerSetup();
                        }
                    }
                });
    }

    /**
     * navigate to the right home and remove according home menu based on user role
     * @param navController navController object of this layout
     */
    private void navigateAndHideAccordingMenuBasedOnRole(NavController navController){
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        //Hide according menu and Navigate to the right fragment based on
        if (currentUserObject.isCustomer()){
            MenuItem driverHomeMenuItem = menu.getItem(1);
            driverHomeMenuItem.setVisible(false);
            navController.navigate(R.id.nav_customer_home);
        } else {
            MenuItem customerHomeMenuItem = menu.getItem(0);
            customerHomeMenuItem.setVisible(false);
            navController.navigate(R.id.nav_driver_home);
        }
    }

    /**
     * Init all child fragments' view models
     */
    private void initAllChildFragmentsViewModel() {
        customerHomeViewModel = ViewModelProviders.of(this).get(CustomerHomeViewModel.class);
        driverHomeViewModel = ViewModelProviders.of(this).get(DriverHomeViewModel.class);
    }

    /**
     * Set nav header username and email
     */
    private void setNavHeaderEmailAndUsername() {
        navHeaderEmailTextView.setText(currentUser.getEmail());
        navHeaderUsernameTextView.setText(currentUserObject.getUsername());
    }

    /**
     * Send current user data through child fragments' view models
     */
    private void setAllChildFragmentsViewModelData() {
        if (currentUserObject.isCustomer()){
            customerHomeViewModel.setCurrentUserObject(currentUserObject);
        } else {
            driverHomeViewModel.setCurrentUserObject(currentUserObject);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
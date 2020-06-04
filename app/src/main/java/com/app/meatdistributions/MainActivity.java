package com.app.meatdistributions;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    BottomNavigationView bottomNavigation;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        //loading the default fragment
        if (getIntent().getStringExtra("fragmentNumber") != null) {
            changeFrangement(Integer.parseInt(getIntent().getStringExtra("fragmentNumber")));
        } else {
            changeFrangement(0);
        }

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        getUSer(currentUser);
    }

    private void getUSer(FirebaseUser user) {
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    private void changeFrangement(int position) {
        switch (position) {
            case 0:
                bottomNavigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                bottomNavigation.setSelectedItemId(R.id.navigation_customers);
                break;
            case 2:
                bottomNavigation.setSelectedItemId(R.id.navigation_products);
                break;
            case 3:
                bottomNavigation.setSelectedItemId(R.id.navigation_shopping);
                break;
            case 4:
                bottomNavigation.setSelectedItemId(R.id.navigation_settings);
                break;
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            loadFragment(new HomeFragment());
                            return true;
                        case R.id.navigation_customers:
                            loadFragment(new CustomerFragment());
                            return true;
                        case R.id.navigation_products:
                            loadFragment(new ProductsFragment());
                            return true;
                        case R.id.navigation_shopping:
                            loadFragment(new ShoppingFragment());
                            return true;
                        case R.id.navigation_settings:
                            loadFragment(new SettingsFragment());
                            return true;
                    }
                    return false;
                }
            };


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
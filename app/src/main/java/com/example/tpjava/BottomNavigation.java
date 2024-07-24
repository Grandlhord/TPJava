//package com.example.tpjava;
//
//import android.os.Bundle;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//import com.example.tpjava.databinding.ActivityBottomNavigationBinding;
//
//public class BottomNavigation extends AppCompatActivity {
//
//    private ActivityBottomNavigationBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Inflate the binding layout and set it as the content view
//        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Set up BottomNavigationView and NavController
//        BottomNavigationView navView = binding.navView; // Use the binding object
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_wallet, R.id.navigation_history, R.id.navigation_notification, R.id.navigation_loans, R.id.navigation_profile)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
//    }
//}

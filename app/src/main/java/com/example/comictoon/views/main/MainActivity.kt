package com.example.comictoon.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.comictoon.R
import com.example.comictoon.api.WifiService
import com.example.comictoon.databinding.ActivityMainBinding
import com.example.comictoon.model.comic.ComicModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {


    companion object {
        lateinit var instance:  MainActivity
    }
    var backPressedTime: Long = 0

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance=this
        // calling the function for WifiService initialization
        setupServices()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController=navHostFragment.navController
        setupActionBarWithNavController(navController)

        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)

        Log.d(TAG,"this is the destiny ${navController.currentDestination.toString()}")



    }
    //this function initializes the WifiService
    private fun setupServices() {
        WifiService.instance.initializeWithApplicationContext(this)
    }

    //

    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp() || super.onSupportNavigateUp()
    }

// this override function is to Double back press to exit from main fragment(ComicFragment.kt)
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
// added this conditon when using NavigationComponent
        if ( navController.currentDestination?.id==navController.graph.startDestination) {
            if(doubleBackToExitPressedOnce)
            super.onBackPressed()
            else{
                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

            }


        }else{
            super.onBackPressed()
        }


    }



    

}
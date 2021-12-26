package com.example.comictoon.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var instance:  MainActivity
    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance=this
        setupServices()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController=navHostFragment.navController
        setupActionBarWithNavController(navController)

        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)


    }
    private fun setupServices() {
        WifiService.instance.initializeWithApplicationContext(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp() || super.onSupportNavigateUp()
    }
}
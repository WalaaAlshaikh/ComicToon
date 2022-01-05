package com.example.comictoon.views.main

import android.content.Context
import android.content.res.Resources
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
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.example.comictoon.R
import com.example.comictoon.api.WifiService
import com.example.comictoon.databinding.ActivityMainBinding
import com.example.comictoon.model.comic.ComicModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), OnLocaleChangedListener {

    private val localizationDelegate = LocalizationActivityDelegate(this)


    companion object {
        lateinit var instance:  MainActivity
    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance=this
        // calling the function for WifiService initialization
        setupServices()

        // for localization
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate()


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
                Toast.makeText(this, getString(R.string.clickagain), Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

            }


        }else{
            super.onBackPressed()
        }


    }

    override fun onAfterLocaleChanged() {
      //
    }

    override fun onBeforeLocaleChanged() {
       //
    }
    public override fun onResume() {
        super.onResume()
        localizationDelegate.onResume(this)
    }

    override fun attachBaseContext(newBase: Context) {
        applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(newBase))
        super.attachBaseContext(newBase)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

    fun setLanguage(language: String?) {
        localizationDelegate.setLanguage(this, language!!)
    }

    fun setLanguage(locale: Locale?) {
        localizationDelegate.setLanguage(this, locale!!)

    }

    val currentLanguage: Locale
        get() = localizationDelegate.getLanguage(this)

}
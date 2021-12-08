package com.example.comictoon.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.comictoon.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        intent= Intent(this,MainActivity::class.java)
        object: CountDownTimer(1000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
               startActivity(intent)

            }

        }.start()
    }


}
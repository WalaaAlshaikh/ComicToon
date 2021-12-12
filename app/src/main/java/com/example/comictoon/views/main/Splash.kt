package com.example.comictoon.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.comictoon.R
import com.example.comictoon.databinding.ActivitySplashBinding
import com.example.comictoon.model.identity.LoginActivity
import com.example.comictoon.model.identity.RegisterActivity
import com.example.comictoon.repositories.ApiReop

class Splash : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiReop.init(this)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.motionLayout.setTransitionListener(object :MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
               ///
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
               ///
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                val intent = Intent(this@Splash, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })
//        intent= Intent(this,MainActivity::class.java)
//        object: CountDownTimer(1000,1000){
//            override fun onTick(p0: Long) {
//
//            }
//
//            override fun onFinish() {
//               startActivity(intent)
//
//            }
//
//        }.start()
    }


}
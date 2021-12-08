package com.example.comictoon.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comictoon.R
import com.example.comictoon.model.comic.ComicModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ref= FirebaseDatabase.getInstance().reference

        ref.setValue("ComicModel")
    }
}
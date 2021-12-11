package com.example.comictoon.adaptersimport

import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.R
import com.example.comictoon.model.comic.Result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder

class MarkedAdapter(private val list: List<Result>) :

    RecyclerView.Adapter<MarkedAdapter.MarkedViewModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarkedAdapter.MarkedViewModel {
        return MarkedViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.maked_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MarkedViewModel, position: Int) {
        val item = list[position]
        val favCollectionRef=Firebase.firestore.collection("users")

        val querySnapshot=favCollectionRef.get()
        val sb=StringBuilder()
        for (document in querySnapshot.result!!.documents){
          //  val favorite=document.toObject<List<>>()

        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MarkedViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}
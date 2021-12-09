package com.example.comictoon.adaptersimport

import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.R
import com.example.comictoon.model.comic.Result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
        TODO("bind view with data")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MarkedViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}
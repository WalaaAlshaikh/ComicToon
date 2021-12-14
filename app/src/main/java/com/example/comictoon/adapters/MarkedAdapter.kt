package com.example.comictoon.adaptersimport

import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.R
import com.example.comictoon.model.comic.Result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.comictoon.model.comic.MarkedModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class MarkedAdapter() :
RecyclerView.Adapter<MarkedAdapter.MarkedViewModel>() {
    val DIFF_CALLBACK=object : DiffUtil.ItemCallback<MarkedModel>(){

        override fun areItemsTheSame(oldItem: MarkedModel, newItem: MarkedModel): Boolean {
           return oldItem==newItem

        }

        override fun areContentsTheSame(oldItem: MarkedModel, newItem: MarkedModel): Boolean {
            return oldItem==newItem
        }


    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
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
        val item = differ.currentList[position]

        holder.title.text=item.title
      Picasso.get().load(item.image).into(holder.image)

//        val favCollectionRef=Firebase.firestore.collection("users")
//
//        val querySnapshot=favCollectionRef.get()
//        val sb=StringBuilder()
//        for (document in querySnapshot.result!!.documents){
//          //  val favorite=document.toObject<List<>>()
//
//        }

        holder.delete.setOnClickListener {

        }



    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submittedList(list: List<MarkedModel>) {
        differ.submitList(list)

    }

    class MarkedViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title:TextView=itemView.findViewById(R.id.marked_title_textView)
        val image:ImageView=itemView.findViewById(R.id.markedimageView)
        val delete:ImageView=itemView.findViewById(R.id.delete_image)
    }
}
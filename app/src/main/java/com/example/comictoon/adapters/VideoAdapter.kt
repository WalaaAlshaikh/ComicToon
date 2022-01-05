package com.example.comictoon.adaptersimport

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.model.videos.Result


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.comictoon.R
import com.example.comictoon.views.videos.VideoDialogFragment
import com.squareup.picasso.Picasso

class VideoAdapter(val context: Context ,
                   val fragmentManger: FragmentManager
) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    val DIFF_CALLBACK=object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem==newItem
        }



    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoAdapter.VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.video_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = differ.currentList[position]

        Picasso.get().load(item.image.mediumUrl).into(holder.image)
        val dialog=VideoDialogFragment()

        holder.image.setOnClickListener {
            dialog.show(fragmentManger, "")
        }


        holder.name.text=item.name
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submittedList(list: List<Result>) {
        differ.submitList(list)

    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image:ImageView=itemView.findViewById(R.id.imagevideo)
        val name:TextView=itemView.findViewById(R.id.videotitle)
    }
}
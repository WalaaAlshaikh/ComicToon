package com.example.comictoon.adaptersimport

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.model.videos.Result


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.comictoon.R

class VideoAdapter(val context: Context) :
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

        var mediaControls: MediaController? = null
        if (mediaControls == null) {
           //  creating an object of media controller class
            mediaControls = MediaController(context)

            // set the anchor view for the video view
            mediaControls.setAnchorView(holder.video)
           holder.video.setMediaController(mediaControls)
          holder.video.setVideoURI(
              Uri.parse(item.highUrl))
            holder.video.requestFocus()
            //holder.video.start()
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
        val video:VideoView=itemView.findViewById(R.id.simpleVideoView)
        val name:TextView=itemView.findViewById(R.id.videotitle)
    }
}
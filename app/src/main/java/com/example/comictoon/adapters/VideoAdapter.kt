package com.example.comictoon.adaptersimport

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.model.videos.Result


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import com.example.comictoon.R

class VideoAdapter(private val list: List<Result>,val context: Context) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
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
        val item = list[position]

        var mediaControls: MediaController? = null
        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(context)

            // set the anchor view for the video view
            mediaControls.setAnchorView(holder.video)
           holder.video.setMediaController(mediaControls)
          holder.video.setVideoURI(
              Uri.parse("https://www.youtube.com/watch?v=${item.youtubeId}"))
            holder.video.requestFocus()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val video:VideoView=itemView.findViewById(R.id.simpleVideoView)
    }
}
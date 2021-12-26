package com.example.comictoon.adaptersimport


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.R
import com.example.comictoon.databinding.ItemLayoutBinding
import com.example.comictoon.model.comic.Result
import com.example.comictoon.views.main.ComicDetailViewModel
import com.example.comictoon.views.main.ComicViewModel
import com.squareup.picasso.Picasso

class ComicAdapter(val comic:ComicDetailViewModel) :
    RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {
    val DIFF_CALLBACK=object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem== newItem
        }


    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComicAdapter.ComicViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            comic.detailComicLiveData.postValue(item)
            comic.listOfResult= item
            holder.itemView.findNavController().navigate(R.id.action_comicsFragment_to_comicsDetailsFragment)

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submittedList(list: List<Result>) {
        differ.submitList(list)

    }

    class ComicViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            // displaying the image in the list
            Picasso.get().load(item.image.originalUrl).into(binding.imageComic)
            binding.ComicTitle.text=item.name
        }

    }
}
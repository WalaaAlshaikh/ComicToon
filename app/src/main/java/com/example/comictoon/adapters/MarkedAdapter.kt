package com.example.comictoon.adaptersimport

import MarkedComicViewModel
import android.app.AlertDialog
import android.content.Context
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
import android.content.DialogInterface

import android.text.Editable
import android.util.Log

import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.example.comictoon.views.main.ComicViewModel
import com.example.comictoon.views.main.UpdateFragment
import com.google.firebase.auth.ktx.auth
import java.lang.IndexOutOfBoundsException

private const val TAG = "MarkedAdapter"

class MarkedAdapter(
    val context: Context,
    val fragmentManger: FragmentManager,
    val comic: MarkedComicViewModel
) :
    RecyclerView.Adapter<MarkedAdapter.MarkedViewModel>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MarkedModel>() {

        override fun areItemsTheSame(oldItem: MarkedModel, newItem: MarkedModel): Boolean {
            return oldItem.comicId == newItem.comicId

        }

        override fun areContentsTheSame(oldItem: MarkedModel, newItem: MarkedModel): Boolean {
            return oldItem == newItem
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

        holder.title.text = item.title
        Picasso.get().load(item.image).into(holder.image)

        holder.delete.setOnClickListener {
            try {
                comic.deleteItem(Firebase.auth.currentUser!!.uid, item.comicId.toString())
            } catch (e: IndexOutOfBoundsException) {
                Log.d(TAG, e.message.toString())
            }

        }
        // To move to detail dialog fragment
        val dialog = UpdateFragment(item.comicId.toString(), item.personalNote.toString())

        holder.update.setOnClickListener {

            dialog.show(fragmentManger, "")

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submittedList(list: List<MarkedModel>) {
        differ.submitList(list)

    }

    class MarkedViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.marked_title_textView)
        val image: ImageView = itemView.findViewById(R.id.markedimageView)
        val delete: ImageView = itemView.findViewById(R.id.delete_image)
        val update: TextView = itemView.findViewById(R.id.personalReview)
    }
}
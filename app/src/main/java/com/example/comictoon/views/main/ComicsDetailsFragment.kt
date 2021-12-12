package com.example.comictoon.views.main

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentComicsDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

private const val TAG = "ComicsDetailsFragment"

class ComicsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentComicsDetailsBinding
    private lateinit var db: FirebaseFirestore
    val comicViewModel: ComicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentComicsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        Log.d(TAG, observer().toString())
        //comicViewModel.callomics()
        db = Firebase.firestore


    }

    fun observer() {
        val db = Firebase.firestore
        val userId = Firebase.auth.currentUser!!.uid
        Log.d("testing", "this is testing of $userId")
        comicViewModel.detailComicLiveData.observe(viewLifecycleOwner, {
            Picasso.get().load(it.image.originalUrl).into(binding.imageView4)
            binding.imageTitle.text = it.name

            fun String.toSpanned(): Spanned {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    @Suppress("DEPRECATION")
                    return Html.fromHtml(this)
                }
            }

            binding.descriptionTextView.text = it.description.toSpanned()
            val comicFav = hashMapOf(
                "image" to it.image.originalUrl,
                "title" to it.name,
                "comicId" to it.id

            )
            Log.d("value", comicFav.getValue("comicId").toString())
            binding.markComic.setOnClickListener {


// orderByChild (id favourite (المفترض الاي دي الي داخل ) equalTo "comicId" { if id favourite موجود  toast else اسوي الي تحت }
                db.collection("users").document("$userId").collection("favourite")
                    .document(comicFav.getValue("comicId").toString()).set(comicFav)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(requireActivity(), "Marked Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        //Log.w(com.example.comictoon.views.identity.TAG, "Error adding document", e)
                    }
                findNavController().navigate(R.id.action_comicsDetailsFragment_to_markedComicFragment)
            }


            binding.publishTextView.text = "Publish year: ${it.startYear}"

        })
    }


}
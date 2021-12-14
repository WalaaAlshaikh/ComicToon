package com.example.comictoon.views.main

import android.annotation.SuppressLint
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import android.text.method.ScrollingMovementMethod
import androidx.navigation.findNavController
import com.example.comictoon.model.comic.Result
import java.lang.Exception


private const val TAG = "ComicsDetailsFragment"

class ComicsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentComicsDetailsBinding

    val comicViewModel: ComicViewModel by activityViewModels()

    private lateinit var list: Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentComicsDetailsBinding.inflate(inflater, container, false)
        observers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.markComic.setOnClickListener {

            comicViewModel.getComicfromClickComic(Firebase.auth.currentUser!!.uid)
        }

    }

    @SuppressLint("SetTextI18n")
    fun observers() {

        comicViewModel.markedLiveData.observe(viewLifecycleOwner, {

            it?.let {
                findNavController().navigate(R.id.action_comicsDetailsFragment_to_markedComicFragment)

//                try {
//
//                }catch (e:Exception){
//                    //
//                }
                Log.d(TAG,"dgd")

                comicViewModel.markedLiveData.postValue(null)

            }

        })


        comicViewModel.detailComicLiveData.observe(viewLifecycleOwner, {
            Picasso.get().load(it.image.originalUrl).into(binding.imageView4)
            binding.imageTitle.text = it.name
            list = it

            try{fun String.toSpanned(): Spanned {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    @Suppress("DEPRECATION")
                    return Html.fromHtml(this)
                }
            }

            binding.descriptionTextView.text = it.description.toSpanned()}
            catch (e:Exception){

                binding.descriptionTextView.setText("This comic has no description")
            }
            binding.descriptionTextView.movementMethod = ScrollingMovementMethod()


            binding.publishTextView.text = "Publish year: ${it.startYear}"

        })

        comicViewModel.comicMarkedErrorLiveData.observe(viewLifecycleOwner,{
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                comicViewModel.comicMarkedErrorLiveData.postValue(null)
            }


        })
    }


}
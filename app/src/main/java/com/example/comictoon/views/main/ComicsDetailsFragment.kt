package com.example.comictoon.views.main

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import com.example.comictoon.model.comic.Result
import com.example.comictoon.util.Notification
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception


private const val TAG = "ComicsDetailsFragment"

class ComicsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentComicsDetailsBinding
    val channelId:String="Channel"
    var notificationId=1
    private val db=Firebase.firestore

    val comicDetailViewModel: ComicDetailViewModel by activityViewModels()
    private lateinit var bottomNav: BottomNavigationView

    private lateinit var resultList: Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bottomNav=activity!!.findViewById(R.id.bottomNavigation)
        bottomNav.visibility=View.VISIBLE
        binding = FragmentComicsDetailsBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        resultList= comicDetailViewModel.listOfResult!!
        Picasso.get().load(resultList.image.originalUrl).into(binding.imageView4)
        binding.imageTitle.text = resultList.name
        db.collection("users").document(Firebase.auth.currentUser!!.uid).collection("favourite")
            .whereEqualTo("comicId",resultList.id).get().addOnSuccessListener {
                if(it.count()> 0){
                    binding.unmarkedImageview.setImageResource(R.drawable.ic_baseline_bookmarks_242)
                }else{
                    binding.unmarkedImageview.setImageResource(R.drawable.ic_baseline_bookmarks_24)
                }
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility=View.GONE
                binding.unmarkedImageview.visibility=View.VISIBLE

            }
        observers()
        try {

            // this function is used to translate the HTML file as a string and then putting it in a Text View
            val convert =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(resultList.description, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    @Suppress("DEPRECATION")
                    Html.fromHtml(resultList.description)
                }


            binding.descriptionTextView.text = convert
        }catch (e:Exception){
            // this will set the text to default string value if there is no description

            binding.descriptionTextView.setText(R.string.noDescription)
        }
        binding.descriptionTextView.movementMethod=LinkMovementMethod.getInstance()

        binding.moreInfoTextView.setOnClickListener {
            val parsrUri= Uri.parse(resultList.siteDetailUrl)
            val intent= Intent(Intent.ACTION_VIEW,parsrUri)
            context!!.startActivity(intent)


        }
        binding.publishTextView.text = "Publish year: ${resultList.startYear}"

        binding.unmarkedImageview.setOnClickListener {

            comicDetailViewModel.getComicfromClickComic(Firebase.auth.currentUser!!.uid)
            binding.unmarkedImageview.setImageResource(R.drawable.ic_baseline_bookmarks_242)
        }


    }

    @SuppressLint("SetTextI18n")
    fun observers() {
        val notifi = Notification(requireContext())

        comicDetailViewModel.markedLiveData.observe(viewLifecycleOwner, {

            it?.let {
                Log.d(TAG,"the observer result")
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()

                notifi.createNotificationChannel("Notice","Marked Comic",notificationId)
                findNavController().navigate(R.id.action_comicsDetailsFragment_to_markedComicFragment)
            }
            comicDetailViewModel.markedLiveData.postValue(null)

        })


//        comicDetailViewModel.detailComicLiveData.observe(viewLifecycleOwner, {
//            it?.let{
//                //Picasso.get().load(it.image.originalUrl).into(binding.imageView4)
//
//                resultList = it
//
//        }
//            comicDetailViewModel.detailComicLiveData.postValue(null)
//
//        })

        comicDetailViewModel.comicDetailErrorLiveData.observe(viewLifecycleOwner,{
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
            comicDetailViewModel.comicDetailErrorLiveData.postValue(null)



        })
    }


}
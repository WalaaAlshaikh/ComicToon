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
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception


private const val TAG = "ComicsDetailsFragment"

class ComicsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentComicsDetailsBinding
    val channelId:String="Channel"
    var notificationId=1

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
        observers()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.unmarkedImageview.setOnClickListener {

            comicDetailViewModel.getComicfromClickComic(Firebase.auth.currentUser!!.uid)
            binding.unmarkedImageview.setImageResource(R.drawable.ic_baseline_bookmarks_242)
        }


    }

    @SuppressLint("SetTextI18n")
    fun observers() {

        comicDetailViewModel.markedLiveData.observe(viewLifecycleOwner, {

            it?.let {
                Log.d(TAG,"the observer result")
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                comicDetailViewModel.markedLiveData.postValue(null)
                createNotificationChannel("Notice","Marked Comic",notificationId)
                findNavController().navigate(R.id.action_comicsDetailsFragment_to_markedComicFragment)
            }

        })


        comicDetailViewModel.detailComicLiveData.observe(viewLifecycleOwner, {
            Picasso.get().load(it.image.originalUrl).into(binding.imageView4)
            binding.imageTitle.text = it.name
            resultList = it

            Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid).collection("favourite")
                .whereEqualTo("comicId",resultList.id).get().addOnSuccessListener {
                    if(it.count()> 0){
                        binding.unmarkedImageview.setImageResource(R.drawable.ic_baseline_bookmarks_242)
                    }else{
                        binding.unmarkedImageview.setImageResource(R.drawable.ic_baseline_bookmarks_24)
                    }
                }

            try {

                // this function is used to translate the HTML file as a string and then putting it in a Text View
                val convert =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(it.description, Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        @Suppress("DEPRECATION")
                        Html.fromHtml(it.description)
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


            binding.publishTextView.text = "Publish year: ${it.startYear}"

        })

        comicDetailViewModel.comicDetailErrorLiveData.observe(viewLifecycleOwner,{
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                comicDetailViewModel.comicDetailErrorLiveData.postValue(null)
            }


        })
    }

    fun createNotificationChannel(name:String,descriptionText:String,id:Int) {
        var builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.bangers)
            .setContentTitle("Comic Toon")
            .setContentText("Yay you liked this comic :)")
            .setStyle(
                NotificationCompat.BigTextStyle()
                .bigText("Yay you liked this comic :)"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name ="Notification"
            val descriptionText ="channel des"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getActivity()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            with(NotificationManagerCompat.from(requireContext())) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())

            }
        }
    }
}
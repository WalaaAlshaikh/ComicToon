package com.example.comictoon.views.videos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.R
import com.example.comictoon.adaptersimport.VideoAdapter
import com.example.comictoon.databinding.FragmentVideoBinding
import com.example.comictoon.model.videos.Result
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.log


private const val TAG = "VideoFragment"
class VideoFragment : Fragment() {
    private lateinit var binding:FragmentVideoBinding
    private lateinit var videoAdapter:VideoAdapter
    private val videoViewModel:VideoViewModel by activityViewModels()
    private var list= mutableListOf<Result>()
    private lateinit var bottomNav: BottomNavigationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNav=activity!!.findViewById(R.id.bottomNavigation)
        bottomNav.visibility=View.VISIBLE
        binding= FragmentVideoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoRecyclerView: RecyclerView =view.findViewById(R.id.video_recyclerView)
        videoAdapter=VideoAdapter(requireActivity())
        videoRecyclerView.adapter=videoAdapter
        videoViewModel.callvideos()



        videoViewModel.videoLiveData.observe(viewLifecycleOwner,{
            videoAdapter.submittedList(it.results)
            list=it.results as MutableList<Result>
          //  binding.videoRecyclerView.animate().alpha(1f)
            Log.d(TAG,list.toString())
        })

        videoViewModel.videoErrorLiveData.observe(viewLifecycleOwner,{
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()

               videoViewModel.videoErrorLiveData.postValue(null)
            }
        })



    }


}
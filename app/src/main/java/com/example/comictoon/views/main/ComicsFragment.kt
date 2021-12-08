package com.example.comictoon.views.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.R
import com.example.comictoon.adaptersimport.ComicAdapter
import com.example.comictoon.databinding.FragmentComicsBinding
import com.example.comictoon.model.comic.Result

private const val TAG = "ComicsFragment"
class ComicsFragment : Fragment() {
    private lateinit var binding:FragmentComicsBinding

    private val comicViewModel:ComicViewModel by activityViewModels()
    private var list= mutableListOf<Result>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentComicsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView =view.findViewById(R.id.comic_recyclerView)
        val comicAdapter= ComicAdapter(comicViewModel)
        recyclerView.adapter=comicAdapter
        comicViewModel.callomics()

        comicViewModel.comicLiveData.observe(viewLifecycleOwner,{
            binding.comicProgressBar.animate().alpha(0f)
            comicAdapter.submittedList(it.results)
        })
    }




}
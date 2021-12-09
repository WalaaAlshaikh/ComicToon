package com.example.comictoon.views.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentComicsDetailsBinding
import com.squareup.picasso.Picasso


class ComicsDetailsFragment : Fragment() {

    private lateinit var binding:FragmentComicsDetailsBinding
    val comicViewModel:ComicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentComicsDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        comicViewModel.callomics()




    }
    
    fun observer(){
        comicViewModel.detailComicLiveData.observe(viewLifecycleOwner,{
            Picasso.get().load(it.image.originalUrl).into(binding.imageView4)
            binding.imageTitle.text=it.name
            binding.descriptionTextView.text=it.description
            binding.publishTextView.text="Publish year: ${it.startYear}"

        })
    }


}
package com.example.comictoon.views.main

import MarkedComicViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentUpdateBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UpdateFragment(val comicId: String, val personalNote: String) : DialogFragment() {

private lateinit var binding:FragmentUpdateBinding
private val comicViewModel:MarkedComicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.saveButton.setOnClickListener {
            var userNote=binding.reviewEditText.text.toString()
            if (personalNote.isNotBlank()) {
                userNote=personalNote
            }
            comicViewModel.updateNote(Firebase.auth.currentUser!!.uid,userNote, comicId)
            comicViewModel.receiveItemFromFireBase(Firebase.auth.currentUser!!.uid)
            dismiss()

        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }



    }
    fun observer(){
//        comicViewModel.markedStringComicLiveData.observe(viewLifecycleOwner,{
//            it?.let {
//                var userNote=binding.reviewEditText.text.toString()
//                comicViewModel.updateNote(Firebase.auth.currentUser!!.uid,userNote, comicId)
//
//                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            }
//
//        })
        comicViewModel.markedComicErrorLiveData.observe(viewLifecycleOwner,{
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                comicViewModel.markedComicErrorLiveData.postValue(null)
            }
        })
    }



}
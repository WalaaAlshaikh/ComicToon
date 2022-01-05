package com.example.comictoon.views.main

import MarkedComicViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentUpdateBinding
import com.example.comictoon.model.comic.MarkedModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UpdateFragment"
class UpdateFragment(val comicId: String, val personalNote: String) : DialogFragment() {

private lateinit var binding:FragmentUpdateBinding
private val updateViewModel:UpdateViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG,comicId)

        // Inflate the layout for this fragment
        binding= FragmentUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.reviewEditText.setText(personalNote)

        binding.saveButton.setOnClickListener {

            var userNote=binding.reviewEditText.text.toString()
            updateViewModel.updateNote(Firebase.auth.currentUser!!.uid,userNote, comicId)

        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
    fun observer(){

        updateViewModel.updateStringLiveData.observe(viewLifecycleOwner,{
            it?.let{

                updateViewModel.updateStringLiveData.postValue(null)
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                dismiss()


        }
        })
        updateViewModel.updateErrorLiveData.observe(viewLifecycleOwner,{
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                updateViewModel.updateErrorLiveData.postValue(null)
            }
        })
    }



}
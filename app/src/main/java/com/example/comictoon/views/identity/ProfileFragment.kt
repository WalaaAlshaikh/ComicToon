package com.example.comictoon.views.identity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentComicsDetailsBinding
import com.example.comictoon.databinding.FragmentProfileBinding
import com.example.comictoon.model.identity.LoginActivity
import com.example.comictoon.views.main.SHARED_PREF_FILE
import com.example.comictoon.views.main.STATE
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrfEditer: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref=requireActivity().getSharedPreferences(SHARED_PREF_FILE,Context.MODE_PRIVATE)
        sharedPrfEditer=sharedPref.edit()
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseAuth.getInstance().currentUser?.let {
            Log.d(TAG, it.displayName.toString())
            binding.userIdTextView.text=it.uid
            binding.emailTextView.text=it.email

        }



        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            sharedPrfEditer.putBoolean(STATE,false).commit()

            startActivity(Intent(requireActivity(),LoginActivity::class.java))


        }


    }


}
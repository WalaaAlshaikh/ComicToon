package com.example.comictoon.views.identity

import android.app.AlertDialog
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrfEditor: SharedPreferences.Editor

    private lateinit var bottomNav:BottomNavigationView
//

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bottomNav=activity!!.findViewById(R.id.bottomNavigation)

        sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        sharedPrfEditor = sharedPref.edit()
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav.visibility=View.GONE


        FirebaseAuth.getInstance().currentUser?.let {
            Log.d(TAG, it.displayName.toString())
            binding.userIdTextView.text = it.displayName
            binding.emailTextView.text = it.email


        }



        binding.logoutButton.setOnClickListener {
            // pop up warning window for confirmation of logging out from account
            val aluilder = AlertDialog.Builder(requireContext())
            aluilder.setTitle("Logout Notification")
            aluilder.setMessage("You are about to logout from your account \n Are you sure?")
            aluilder.setPositiveButton("Yes") { dialogInterface, which ->
                FirebaseAuth.getInstance().signOut()
                sharedPrfEditor.putBoolean(STATE, false).commit()

                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }

            aluilder.setNegativeButton("No") { dialogInterface, which ->

            }
            val theDialog: AlertDialog = aluilder.create()
            theDialog.setCancelable(false)
            theDialog.show()




        }

    }


}
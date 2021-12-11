package com.example.comictoon.views.identity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {


private lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToRegisTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener {

            val emailAdress=binding.lEmailEditText.text.toString()
            val password=binding.lPasswordEditText.text.toString()
            if (emailAdress.isNotBlank() && password.isNotBlank())
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailAdress,password).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(requireActivity(), "Login Successfully !", Toast.LENGTH_SHORT).show()
//                        val user= Firebase.auth.currentUser
//                        Log.d("testing","this is testing of $user" )


                        findNavController().navigate(R.id.action_loginFragment_to_comicsFragment)


                    }else{

                    Toast.makeText(requireActivity(), it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }




}
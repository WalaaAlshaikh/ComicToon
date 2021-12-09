package com.example.comictoon.views.identity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment() {

    private lateinit var binding:FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToLoginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.registerButton.setOnClickListener {
            val registerEmail=binding.rEmailEditText.text.toString()
            val registerEmailConfirm=binding.rEmailconfEditText.text.toString()
            val registerPassword=binding.rPasswordEditText.text.toString()
            val registerPasswordConfirm=binding.rPasswordconfEditText.text.toString()
            if (registerEmail.isNotBlank() && registerEmailConfirm.isNotBlank() && registerPassword.isNotBlank() && registerPasswordConfirm.isNotBlank()){
                if(registerEmail== registerEmailConfirm && registerPassword == registerPasswordConfirm){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(registerEmail,registerPassword).addOnCompleteListener{
                        if(it.isSuccessful){
                            val firebaseUser: FirebaseUser =it.result!!.user!!
                            Toast.makeText(requireActivity(), "User Registered Successfully", Toast.LENGTH_SHORT).show()
                            val bundle= bundleOf("userId" to firebaseUser.uid, "email" to firebaseUser.email)

                            findNavController().navigate(R.id.action_registerFragment_to_profileFragment, bundle)

                        }else{
                            Toast.makeText(requireActivity(), it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }

                    }
                }else{
                    Toast.makeText(requireActivity(), "Either email or password fields are not matched", Toast.LENGTH_SHORT).show()
                }


            }else{
                Toast.makeText(requireActivity(), "Registration fields must not be empty", Toast.LENGTH_SHORT).show()
            }
        }


    }


}
package com.example.comictoon.model.identity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.comictoon.R
import com.example.comictoon.databinding.ActivityRegisterBinding
import com.example.comictoon.views.main.MainActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "RegisterActivity"
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        binding.goToLoginTextView.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.registerButton.setOnClickListener {
            val registerEmail = binding.rEmailEditText.text.toString()
            val registerEmailConfirm = binding.rEmailconfEditText.text.toString()
            val registerPassword = binding.rPasswordEditText.text.toString()
            val registerPasswordConfirm = binding.rPasswordconfEditText.text.toString()
            if (registerEmail.isNotBlank() && registerEmailConfirm.isNotBlank() && registerPassword.isNotBlank() && registerPasswordConfirm.isNotBlank()) {
                if (registerEmail == registerEmailConfirm && registerPassword == registerPasswordConfirm) {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(registerEmail, registerPassword)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val firebaseUser: FirebaseUser = it.result!!.user!!
                                Toast.makeText(
                                    this,
                                    "User Registered Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()


//                                val bundle = bundleOf(
//                                    "userId" to firebaseUser.uid,
//                                    "email" to firebaseUser.email
//                                )
//                                Log.d("register", bundle.toString())

                                val user = hashMapOf(
                                    "email" to registerEmail,
                                    "password" to registerPassword
                                )

// Add a new document with a generated ID
                                db.collection("users").document("${firebaseUser.uid}").set(user)
                                    .addOnSuccessListener { documentReference ->
                                        //
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }

                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("UserId", firebaseUser.uid)
                                intent.putExtra("Email", firebaseUser.email)

                                startActivity(intent)
                                finish()


                            } else {
                                Toast.makeText(
                                    this,
                                    it.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                } else {
                    Toast.makeText(
                        this,
                        "Either email or password fields are not matched",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            } else {
                Toast.makeText(
                    this,
                    "Registration fields must not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
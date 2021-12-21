package com.example.comictoon.model.identity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.comictoon.R
import com.example.comictoon.databinding.ActivityRegisterBinding
import com.example.comictoon.repositories.FireBaseRepo
import com.example.comictoon.util.RegisterValidation
import com.example.comictoon.views.identity.RegisterViewModel
import com.example.comictoon.views.main.MainActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "RegisterActivity"

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private val validator = RegisterValidation()
    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.apply {
            setTitle("Loading...")
            setCancelable(false)
        }
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.goToLoginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        observer()
        binding.registerButton.setOnClickListener {
            val registerEmail = binding.rEmailEditText.text.toString()
            val registerUsername = binding.rUsernameEditText.text.toString()
            val registerPassword = binding.rPasswordEditText.text.toString()
            val registerPasswordConfirm = binding.rPasswordconfEditText.text.toString()
            if (registerPassword.isNotBlank() && registerPasswordConfirm.isNotBlank()) {
                if (registerPassword == registerPasswordConfirm) {
                    if (validator.emailIsValid(registerEmail)) {
                        if (validator.passIsValid(registerPassword)) {

                            progressDialog.show()
                            firebaseAuth
                                .createUserWithEmailAndPassword(registerEmail, registerPassword)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        val firebaseUser: FirebaseUser = it.result!!.user!!

                                        var profileUpdate = UserProfileChangeRequest.Builder()
                                            .setDisplayName(registerUsername).build()
                                        Toast.makeText(
                                            this,
                                            "User Registered Successfully",
                                            Toast.LENGTH_SHORT


                                        ).show()

                                        val user = hashMapOf(
                                            "email" to registerEmail,
                                            "password" to registerPassword,
                                            "displayname" to registerUsername
                                        )


// Add a new document with a generated ID
                                        registerViewModel.userInfo(firebaseUser.uid, user)
                                        firebaseAuth.currentUser?.updateProfile(profileUpdate)

                                        val intent = Intent(this, MainActivity::class.java)
                                        Log.d(TAG, firebaseUser.displayName.toString())

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
                                "Your password should be strong ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(
                            this,
                            "Your email address should be correct",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        this,
                        "Password fields are not matched",
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

    fun observer() {
        registerViewModel.registerLiveData.observe(this, {
            it?.let {

                progressDialog.dismiss()

                registerViewModel.registerLiveData.postValue(null)
            }
        })

        registerViewModel.registerErrorLiveData.observe(this, {
            it?.let {
                progressDialog.dismiss()
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                registerViewModel.registerErrorLiveData.postValue(null)
            }
        })
    }
}
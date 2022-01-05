package com.example.comictoon.model.identity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
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

    private lateinit var firebaseAuth: FirebaseAuth
    private val validator = RegisterValidation()
    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        val dialog = setProgressDialog(this, "Loading..")
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.goToLoginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        observer()
        binding.registerButton.setOnClickListener {
            val registerEmail = binding.rEmailTextfield.editText?.text.toString()

            val registerUsername = binding.rUsernameTextfield.editText?.text.toString()
            val registerPassword = binding.rPasswordTextfield.editText?.text.toString()
            val registerPasswordConfirm = binding.rPasswordConTextfield.editText?.text.toString()
          if (registerPassword.isNotBlank() && registerPasswordConfirm.isNotBlank() && registerEmail.isNotBlank() && registerUsername.isNotBlank())  {

              if (registerPassword == registerPasswordConfirm ) {

                      if (validator.passIsValid(registerPassword)) {
                            dialog.show()
                            firebaseAuth
                                .createUserWithEmailAndPassword(registerEmail, registerPassword)
                                .addOnCompleteListener {


                                    if (it.isSuccessful) {
                                        val firebaseUser: FirebaseUser = it.result!!.user!!

                                        var profileUpdate = UserProfileChangeRequest.Builder()
                                            .setDisplayName(registerUsername).build()
                                        Toast.makeText(
                                            this,
                                            getString(R.string.sucessRegi),
                                            Toast.LENGTH_SHORT


                                        ).show()

                                        val user = hashMapOf(
                                            "email" to registerEmail,
                                            "password" to registerPassword,
                                            "displayname" to registerUsername
                                        )


                                        registerViewModel.userInfo(firebaseUser.uid, user)
                                        // to get the data from registration and updating it in the profile
                                        firebaseAuth.currentUser?.updateProfile(profileUpdate)
                                        // navigate to the login page after registration
                                        val intent = Intent(this, LoginActivity::class.java)
                                        Log.d(TAG, firebaseUser.displayName.toString())
                                        startActivity(intent)
                                        finish()
                                    }  else {

                                        dialog.hide()
                                        binding.rEmailTextfield.error=it.exception!!.message.toString()


                                    }

                                }
                        } else {


                            binding.rPasswordTextfield.error=getString(R.string.strongPass)
                        }

                } else {
                    binding.rPasswordConTextfield.error=getString(R.string.matchPass)
                    binding.rPasswordTextfield.error=getString(R.string.matchPass)

                }


            } else {

                checkFields(registerUsername,registerEmail,registerPassword,registerPasswordConfirm)

            }
        }

    }

    fun observer() {
        registerViewModel.registerLiveData.observe(this, {
            it?.let {

                registerViewModel.registerLiveData.postValue(null)
            }
        })

        registerViewModel.registerErrorLiveData.observe(this, {
            Log.d(TAG,it.toString())
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    fun setProgressDialog(context: Context, message:String): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        return dialog
    }

    private fun checkFields(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var state = true
        val emailLayout = binding.rEmailTextfield
        val fullNameLayout = binding.rUsernameTextfield
        val passwordLayout = binding.rPasswordTextfield
        val confirmPassLayout = binding.rPasswordConTextfield

        emailLayout.error = null
        fullNameLayout.error = null
        passwordLayout.error = null
        confirmPassLayout.error = null

        // Get needed string messages from strings.xml resource
        val require = getString(R.string.require)
        val wrongEmailFormat = getString(R.string.emailcheck)
        val passwordConditions = getString(R.string.matchPass)

        if (fullName.isBlank()) {
            fullNameLayout.error = require
            state = false
        }

        if (email.isBlank()) {
            emailLayout.error = require
            state = false
        } else if (!validator.emailIsValid(email)) {
            emailLayout.error = wrongEmailFormat
            state = false
        }

        if (password.isBlank()) {
            passwordLayout.error = require
            state = false
        } else if (!validator.passIsValid(password)) {
            passwordLayout.error = passwordConditions
            state = false
        }

        if (confirmPassword.isBlank()) {
            confirmPassLayout.error = require
            state = false
        }

        return state
    }
}
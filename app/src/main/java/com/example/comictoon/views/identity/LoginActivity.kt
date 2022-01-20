package com.example.comictoon.views.identity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.example.comictoon.R
import com.example.comictoon.databinding.ActivityLoginBinding
import com.example.comictoon.views.main.MainActivity
import com.example.comictoon.views.main.SHARED_PREF_FILE
import com.example.comictoon.views.main.STATE
import com.example.comictoon.views.main.USER_ID
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        val sharedPrefEdit = sharedPref.edit()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToRegisTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {

            val emailAddress = binding.emailTextfield.editText?.text.toString()
            val password = binding.passwordTextfield.editText?.text.toString()
            if (emailAddress.isNotBlank() && password.isNotBlank()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Login Successfully !", Toast.LENGTH_SHORT).show()

                            sharedPrefEdit.putBoolean(STATE, true)
                            sharedPrefEdit.putString(USER_ID,FirebaseAuth.getInstance().currentUser!!.uid)
                            sharedPrefEdit.commit()
                            val intent = Intent(this, MainActivity::class.java)
//                            intent.putExtra("UserId", FirebaseAuth.getInstance().currentUser!!.uid)
//                            intent.putExtra("Email", FirebaseAuth.getInstance().currentUser!!.email)
                            startActivity(intent)
                            finish()

                        } else {

                            binding.passwordTextfield.error="Either Email or Password is incorrect)"
                        }
                    }

        }else{
                checkFields(emailAddress,password)
        }
    }
        binding.forgotPassTextView.setOnClickListener {
            resetPassDialog()

        }
}

    private fun checkFields(
        email: String,
        password: String,
    ): Boolean {
        var state = true
        val emailLayout = binding.emailTextfield
        val passwordLayout = binding.passwordTextfield

        emailLayout.error = null

        passwordLayout.error = null

        // Get needed string messages from strings.xml resource
        val require = "required!"

        if (email.isBlank()) {
            emailLayout.error = require
            state = false
        }

        if (password.isBlank()) {
            passwordLayout.error = require
            state = false
        }


        return state
    }


    fun resetPassDialog() {
        val builder: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.resetPass))

// Set up the input
        val input = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint(getString(R.string.enterEmail))
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton(
            getString(R.string.sendPass),
            DialogInterface.OnClickListener { dialog, which ->
                // Here you get get input text from the Edittext
                val email = input.text.toString()
                if (email.isNotEmpty()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(input.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    getString(R.string.passsent),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    it.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.putEmail),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        builder.setNegativeButton(
            getString(R.string.Cancel),
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
}
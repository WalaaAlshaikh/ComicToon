package com.example.comictoon.views.identity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
}
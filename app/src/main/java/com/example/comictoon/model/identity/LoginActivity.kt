package com.example.comictoon.model.identity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
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
        val sharedPferfEdit = sharedPref.edit()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToRegisTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {

            val emailAdress = binding.lEmailEditText.text.toString()
            val password = binding.lPasswordEditText.text.toString()
            if (emailAdress.isNotBlank() && password.isNotBlank())
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailAdress, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Login Successfully !", Toast.LENGTH_SHORT).show()

                            sharedPferfEdit.putBoolean(STATE, true)
                            sharedPferfEdit.putString(USER_ID,FirebaseAuth.getInstance().currentUser!!.uid)
                            sharedPferfEdit.commit()
                            val intent = Intent(this, MainActivity::class.java)
//                            intent.putExtra("UserId", FirebaseAuth.getInstance().currentUser!!.uid)
//                            intent.putExtra("Email", FirebaseAuth.getInstance().currentUser!!.email)
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

        }
    }
}
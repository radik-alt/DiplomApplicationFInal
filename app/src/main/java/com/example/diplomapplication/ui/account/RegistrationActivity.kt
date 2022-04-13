package com.example.diplomapplication.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.diplomapplication.MainActivity
import com.example.diplomapplication.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        binding.registerAccount.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (validUser(email, password)){

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Такого аккаунта нет!", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        binding.intentLogIn.setOnClickListener {
            val logInIntent = Intent(this, LogInActivity::class.java)
            startActivity(logInIntent)
            finish()
        }
    }

    private fun validUser(email: String, password: String): Boolean {
        if (email.isNotBlank() && password.isNotBlank()) {
            Toast.makeText(this, "Не ввели логин или пароль!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
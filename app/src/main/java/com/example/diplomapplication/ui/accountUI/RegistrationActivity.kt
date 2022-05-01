package com.example.diplomapplication.ui.accountUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.diplomapplication.MainActivity
import com.example.diplomapplication.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var UID : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // убрать ActionBar
        supportActionBar?.hide()

        // подключение FirebaseAuth (сервер для подключения для регистрации)
        auth = FirebaseAuth.getInstance()


        // регистрация пользователя в firebase,
        // так же взятие email and password с edittext
        // сервер сам проверяет корректность данных,
        // единственное мы пишем валидация на пустые строки
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

        // переход к экрану авториазции
        binding.intentLogIn.setOnClickListener {
            val logInIntent = Intent(this, LogInActivity::class.java)
            startActivity(logInIntent)
            finish()
        }
    }

    // валидация на пустые строки
    private fun validUser(email: String, password: String): Boolean {
        if (email.isBlank() && password.isBlank()) {
            Toast.makeText(this, "Не ввели логин или пароль!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
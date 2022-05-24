package com.example.diplomapplication.ui.accountUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.diplomapplication.MainActivity
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.ActivityLogInBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // убрать ActionBar
        supportActionBar?.hide()

        // подключение FirebaseAuth (сервер для подключения для регистрации)
        auth = FirebaseAuth.getInstance()


        // Возврат назад к окну авториазации
        binding.backRegisterActivity.setOnClickListener {
            val backRegisterIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(backRegisterIntent)
            finish()
        }

        // авториазция пользователя по логину и паролю
        // взятие email и password с edittext
        binding.LogInAccount.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (validUser(email, password)){

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Не ввели логин или пароль!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        // по кнопке вспомнить пароль, выводит окно BottomSheetDialog,
        // в котором вводим email и он отправляет ссылку для сброса пароля
        binding.returnPassword.setOnClickListener {

            val bottomsheet = BottomSheetDialog(this)
            bottomsheet.setContentView(R.layout.remember_password)

            val sendEmail = bottomsheet.findViewById<Button>(R.id.sendEmail)
            val editEmail = bottomsheet.findViewById<EditText>(R.id.remember_email)

            sendEmail!!.setOnClickListener {
                val email = editEmail!!.text.toString()

                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Выслали новый пароль на вашу почту!", Toast.LENGTH_SHORT).show()
                        bottomsheet.cancel()
                    } else {
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                        bottomsheet.cancel()
                    }
                }

            }
            bottomsheet.show()


        }

    }

    private fun validUser(email: String, password:String): Boolean {
        if (email.isBlank() and password.isBlank()) {
            return false
        }
        return true
    }
}
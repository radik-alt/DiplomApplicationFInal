package com.example.diplomapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.diplomapplication.databinding.ActivityMainBinding
import com.example.diplomapplication.ui.ToDoIstUI.Interface.ChangeNavigation
import com.example.diplomapplication.ui.accountUI.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentUser : String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // проверка авторриазции пользователя firebase
        // и получение его UID (уникальный индификатор)
        auth = FirebaseAuth.getInstance()
        currentUser = auth.uid.toString()

        setSupportActionBar(binding.appBarMain.toolbar)

        // DrawLayout, создание выдвигающегося окна с переходами
        // на таймер, todoist и заметки
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        // если Firebase скажет, что пользователя не существует,
        // то переход на активити регистрации
        if (currentUser == "null" || currentUser == null){
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        super.onStart()
    }


}
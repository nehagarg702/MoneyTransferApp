package com.example.moneytransferapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.moneytransferapp.ui.screen.LoginScreen
import com.example.moneytransferapp.utils.NetworkUtils
import com.example.moneytransferapp.viewmodel.UserViewModel
import org.koin.androidx.compose.get

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (checkLoginStatus(this)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                val userViewModel: UserViewModel = get()
                val networkUtils : NetworkUtils = get()
                LoginScreen(userViewModel = userViewModel, this, networkUtils ) { user ->
                    saveLoginStatus(this@LoginActivity, true)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    // Function to save login status
    fun saveLoginStatus(context: Context, isLoggedIn: Boolean) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }



    fun checkLoginStatus(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
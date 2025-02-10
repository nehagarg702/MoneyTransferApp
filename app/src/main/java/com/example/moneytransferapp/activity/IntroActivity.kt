package com.example.moneytransferapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.moneytransferapp.ui.components.IntroSlider

class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (checkIntroStatus(this)) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                IntroSlider(rememberNavController()) {
                    saveIntroStatus(this@IntroActivity, true)
                    startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }

    fun saveIntroStatus(context: Context, hasSeenIntro: Boolean) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("hasSeenIntro", hasSeenIntro).apply()
    }

    fun checkIntroStatus(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("hasSeenIntro", false)
    }
}
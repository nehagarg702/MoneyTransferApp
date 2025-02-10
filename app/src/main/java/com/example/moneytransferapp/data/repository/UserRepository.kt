package com.example.moneytransferapp.data.repository

import android.content.Context
import android.content.SharedPreferences

class UserRepository(context: Context) : IUserRepository {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    override fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}


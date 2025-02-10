package com.example.moneytransferapp

import android.app.Application
import android.content.Context
import com.example.moneytransferapp.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MoneyTransferApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        checkFirstRun()
        GlobalContext.startKoin {
            androidContext(this@MoneyTransferApplication)
            modules(appModule)
        }
    }

    private fun checkFirstRun() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("is_first_run", true)

        if (isFirstRun) {
            sharedPreferences.edit().putBoolean("is_first_run", false).apply()
        }
    }
}

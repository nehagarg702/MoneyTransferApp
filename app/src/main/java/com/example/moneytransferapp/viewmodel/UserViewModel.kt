package com.example.moneytransferapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.moneytransferapp.data.repository.IUserRepository
import com.example.moneytransferapp.utils.NetworkUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class UserViewModel(
    private val userRepository: IUserRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var errorMessage: String? = null

    fun loginWithEmailPassword(username: String, password: String, onLoginSuccess: (FirebaseUser) -> Unit, onError: (String) -> Unit) {
        // Check if network is available
        if (!networkUtils.isNetworkAvailable()) {
            errorMessage = "No internet connection"
            onError(errorMessage ?: "")
            return
        }

        try {
            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            userRepository.saveLoginStatus(true)
                            onLoginSuccess(user)
                        }
                    } else {
                        val exception = task.exception
                        val errorMsg = when (exception) {
                            is FirebaseAuthException -> exception.message ?: "Authentication failed"
                            else -> "Unknown error occurred"
                        }
                        errorMessage = errorMsg
                        onError(errorMsg)
                    }
                }
        } catch (e: Exception) {
            Log.e("Login", "Error during login", e)
            errorMessage = "An error occurred while trying to log in"
            onError(errorMessage ?: "")
        }
    }

    fun signUpWithEmailPassword(email: String, password: String, onSignUpSuccess: (FirebaseUser) -> Unit, onError: (String) -> Unit) {
        // Check if network is available
        if (!networkUtils.isNetworkAvailable()) {
            errorMessage = "No internet connection"
            onError(errorMessage ?: "")
            return
        }

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            userRepository.saveLoginStatus(true)
                            onSignUpSuccess(user)
                        }
                    } else {
                        val exception = task.exception
                        val errorMsg = when (exception) {
                            is FirebaseAuthException -> exception.message ?: "Sign-up failed"
                            else -> "Unknown error occurred"
                        }
                        errorMessage = errorMsg
                        onError(errorMsg)
                    }
                }
        } catch (e: Exception) {
            Log.e("SignUp", "Error during sign-up", e)
            errorMessage = "An error occurred while trying to sign up"
            onError(errorMessage ?: "")
        }
    }
}

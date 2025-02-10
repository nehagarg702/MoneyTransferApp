package com.example.moneytransferapp.utils

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthHelper {
    private val auth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }
}

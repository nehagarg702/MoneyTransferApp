package com.example.moneytransferapp.ui.screen

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.moneytransferapp.utils.NetworkUtils
import com.example.moneytransferapp.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    context: Context,
    networkUtils: NetworkUtils,
    onLoginSuccess: (FirebaseUser) -> Unit
) {
    var loginMethod by remember { mutableStateOf("none") }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task, onLoginSuccess, context, networkUtils)
        }
    }

    LaunchedEffect(email, password) {
        isButtonEnabled = email.text.isValidEmail() && password.text.isValidPassword()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to the App", style = MaterialTheme.typography.h4, modifier = Modifier.padding(bottom = 32.dp))

        if (loginMethod == "none") {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { loginMethod = "email" },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Login with Email")
                }

                Button(
                    onClick = { googleSignIn(googleSignInLauncher, context, networkUtils) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login with Google")
                }
            }
        }

        if (loginMethod == "email") {
            EmailLoginSection(
                email = email,
                password = password,
                emailError = emailError,
                passwordError = passwordError,
                onEmailChanged = {
                    email = it
                    emailError = if (it.text.isValidEmail()) "" else "Invalid email format"
                },
                onPasswordChanged = {
                    password = it
                    passwordError = if (it.text.isValidPassword()) "" else "Password must be at least 6 characters"
                },
                onSignInClick = {
                    if (isButtonEnabled) {
                        userViewModel.loginWithEmailPassword(
                            email.text, password.text,
                            onLoginSuccess = { user -> onLoginSuccess(user) },
                            onError = { error -> showToast(context, error) }
                        )
                    }
                },
                onSignUpClick = {
                    if (isButtonEnabled) {
                        userViewModel.signUpWithEmailPassword(
                            email.text, password.text,
                            onSignUpSuccess = { user -> onLoginSuccess(user) },
                            onError = { error -> showToast(context, error) }
                        )
                    }
                },
                isButtonEnabled = isButtonEnabled
            )
        }
    }
}

// Show Toast for error messages
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

// Google Sign-In function
private fun googleSignIn(googleSignInLauncher: ActivityResultLauncher<Intent>, context: Context, networkUtils: NetworkUtils) {
    if (!networkUtils.isNetworkAvailable()) {
        showToast(context, "No internet connection. Please check your network and try again.")
        return
    }
    val remoteConfig = Firebase.remoteConfig

    // Set default values (optional)
    val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600 // Fetch every hour
    }
    remoteConfig.setConfigSettingsAsync(configSettings)
    remoteConfig.setDefaultsAsync(mapOf("google_sign_in_client_id" to "")) // Default empty

    remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val clientId = remoteConfig.getString("google_sign_in_client_id")
            if (clientId.isNotEmpty()) {
                startGoogleSignIn(clientId, googleSignInLauncher, context, networkUtils)
            } else {
                showToast(context, "Google Sign-In client ID not found")
            }
        } else {
            showToast(context, "Something Went wrong. Please try again later.")
        }
    }
}

private fun startGoogleSignIn(clientId: String, googleSignInLauncher: ActivityResultLauncher<Intent>, context: Context, networkUtils: NetworkUtils) {
    if (!networkUtils.isNetworkAvailable()) {
        showToast(context, "No internet connection. Please check your network and try again.")
        return
    }
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(clientId)
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val signInIntent = googleSignInClient.signInIntent
    googleSignInLauncher.launch(signInIntent)
}

// Handle the sign-in result
private fun handleSignInResult(task: Task<GoogleSignInAccount>, onLoginSuccess: (FirebaseUser) -> Unit, context: Context, networkUtils: NetworkUtils) {
    try {
        val account = task.getResult(ApiException::class.java)
        firebaseAuthWithGoogle(account.idToken!!, onLoginSuccess, context, networkUtils)
    } catch (e: ApiException) {
        showToast(context, "Google sign-in failed: ${e.statusCode}")
    }
}

// Firebase authentication with Google credentials
private fun firebaseAuthWithGoogle(idToken: String, onLoginSuccess: (FirebaseUser) -> Unit, context: Context, networkUtils: NetworkUtils) {
    if (!networkUtils.isNetworkAvailable()) {
        showToast(context, "No internet connection. Please check your network and try again.")
        return
    }
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    onLoginSuccess(user)
                }
            } else {
                showToast(context, "Authentication failed: ${task.exception?.message}")
            }
        }
}

package com.example.moneytransferapp.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun EmailLoginSection(
    email: TextFieldValue,
    password: TextFieldValue,
    emailError: String,
    passwordError: String,
    onEmailChanged: (TextFieldValue) -> Unit,
    onPasswordChanged: (TextFieldValue) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    isButtonEnabled: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email Field
        Column {
            BasicTextField(
                value = email,
                onValueChange = { onEmailChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.small)
                    .padding(16.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (email.text.isEmpty()) {
                            Text(
                                text = "Enter email",
                                style = MaterialTheme.typography.body1.copy(color = Color.Gray)
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (emailError.isNotEmpty()) {
                Text(
                    text = emailError,
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.error)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        Column {
            BasicTextField(
                value = password,
                onValueChange = {
                    onPasswordChanged(it)
                                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.small)
                    .padding(16.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (password.text.isEmpty()) {
                            Text(
                                text = "Enter password",
                                style = MaterialTheme.typography.body1.copy(color = Color.Gray)
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (passwordError.isNotEmpty()) {
                Text(
                    text = passwordError,
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.error)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign In and Sign Up Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onSignInClick, enabled = isButtonEnabled) {
                Text("Sign In")
            }

            Button(onClick = onSignUpClick, enabled = isButtonEnabled) {
                Text("Sign Up")
            }
        }
    }
}


// Email and password validation functions
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}

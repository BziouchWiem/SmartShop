package com.wiem.smartshop.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignupScreen(authViewModel: AuthViewModel = viewModel(), onSignupSuccess: () -> Unit) {
    val email by authViewModel.email
    val password by authViewModel.password
    val loading by authViewModel.loading
    val errorMessage by authViewModel.errorMessage
    val isLoggedIn by authViewModel.isLoggedIn

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextField(
                value = email,
                onValueChange = { authViewModel.email.value = it },
                label = { Text("Email") },
                singleLine = true
            )
            TextField(
                value = password,
                onValueChange = { authViewModel.password.value = it },
                label = { Text("Mot de passe") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            if (errorMessage != null) {
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            }

            Button(onClick = { authViewModel.signup() }, enabled = !loading) {
                if (loading) CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                else Text("S’inscrire")
            }

            if (isLoggedIn) {
                onSignupSuccess()
            }
        }
    }
}

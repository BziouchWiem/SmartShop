package com.wiem.smartshop.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // Firebase Auth instance
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // UI state
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val loading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val isLoggedIn = mutableStateOf(false)

    fun login() {
        loading.value = true
        errorMessage.value = null

        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    loading.value = false
                    if (task.isSuccessful) {
                        isLoggedIn.value = true
                    } else {
                        errorMessage.value = task.exception?.localizedMessage
                    }
                }
        }
    }
    fun signup() {
        loading.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                loading.value = false
                if (task.isSuccessful) {
                    isLoggedIn.value = true
                } else {
                    errorMessage.value = task.exception?.message
                }
            }
    }

}

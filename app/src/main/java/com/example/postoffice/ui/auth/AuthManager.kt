package com.example.postoffice.ui.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class User(
    val displayName: String?,
    val email: String?
)

object AuthManager {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun init(context: Context) {
        // Initialize AuthManager
    }

    fun signOut() {
        _currentUser.value = null
    }
}

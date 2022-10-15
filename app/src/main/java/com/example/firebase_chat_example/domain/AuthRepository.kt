package com.example.firebase_chat_example.domain

import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val current: FirebaseUser?
        get() = null

    suspend fun signIn(email: String, password: String): Resource<FirebaseUser>
    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Resource<FirebaseUser>

    suspend fun getUser():Resource<FirebaseUser?>

    fun logout()
}
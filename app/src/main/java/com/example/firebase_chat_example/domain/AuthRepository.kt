package com.example.firebase_chat_example.domain

import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val current: FirebaseUser?
        get() = null

    suspend fun signIn(user:UserModel): Resource<FirebaseUser>
    suspend fun signUp(
        user:UserModel
    ): Resource<FirebaseUser>

    suspend fun getUser():Resource<FirebaseUser?>

    fun logout()
}
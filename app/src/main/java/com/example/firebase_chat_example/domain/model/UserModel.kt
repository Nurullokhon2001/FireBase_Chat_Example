package com.example.firebase_chat_example.domain.model

data class UserModel(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val uid: String = "",
    val profileImage: String = ""
)
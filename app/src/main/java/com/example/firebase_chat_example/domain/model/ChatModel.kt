package com.example.firebase_chat_example.domain.model

data class ChatModel(
    val senderId: String = "",
    val receiverId: String ="",
    val message: String = "",
    val time: Long = 0
)
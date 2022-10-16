package com.example.firebase_chat_example.domain


interface ChatRepository {
    suspend  fun addUser(userName:String)
}
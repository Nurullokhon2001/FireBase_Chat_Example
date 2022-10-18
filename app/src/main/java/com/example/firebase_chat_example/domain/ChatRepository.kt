package com.example.firebase_chat_example.domain

import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource


interface ChatRepository {
    suspend  fun addUser(userModel:UserModel)
    suspend  fun getUsers(): Resource<List<UserModel>>
}
package com.example.firebase_chat_example.domain

import com.example.firebase_chat_example.domain.model.ChatModel
import com.example.firebase_chat_example.domain.model.PushNotificationModel
import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource
import okhttp3.ResponseBody
import retrofit2.Response


interface ChatRepository {
    suspend fun addUser(userModel: UserModel)
    suspend fun sendMessage(chatModel: ChatModel)
    suspend fun readMessage(receiverId: String): Resource<List<ChatModel>>
    suspend fun getUsers(): Resource<List<UserModel>>
    suspend fun sendPushNotification(notification: PushNotificationModel): Response<ResponseBody>
}
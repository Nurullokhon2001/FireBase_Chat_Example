package com.example.firebase_chat_example.domain.chat_use_case

import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.PushNotificationModel
import javax.inject.Inject

class SendPushNotificationUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(notification: PushNotificationModel) {
     repository.sendPushNotification(notification)
    }
}
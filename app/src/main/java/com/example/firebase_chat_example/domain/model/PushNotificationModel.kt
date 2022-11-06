package com.example.firebase_chat_example.domain.model

data class PushNotificationModel(
    var data: NotificationDataModel,
    var to:String
)
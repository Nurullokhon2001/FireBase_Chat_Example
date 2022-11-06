package com.example.firebase_chat_example.data

import com.example.firebase_chat_example.domain.model.PushNotificationModel
import com.example.firebase_chat_example.utils.Constants.Companion.CONTENT_TYPE
import com.example.firebase_chat_example.utils.Constants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {

    @Headers("Authorization: key=$SERVER_KEY","Content-type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotificationModel
    ): Response<ResponseBody>
}
package com.example.firebase_chat_example.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_chat_example.domain.auth_use_case.GetUserUseCase
import com.example.firebase_chat_example.domain.chat_use_case.ReadMessageUseCase
import com.example.firebase_chat_example.domain.chat_use_case.SendMessageUseCase
import com.example.firebase_chat_example.domain.chat_use_case.SendPushNotificationUseCase
import com.example.firebase_chat_example.domain.model.ChatModel
import com.example.firebase_chat_example.domain.model.NotificationDataModel
import com.example.firebase_chat_example.domain.model.PushNotificationModel
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val readMessageUseCase: ReadMessageUseCase,
    private val sendPushNotificationUseCase: SendPushNotificationUseCase,
) : ViewModel() {

    private val _user = MutableLiveData<Resource<FirebaseUser?>>()
    val user: LiveData<Resource<FirebaseUser?>> get() = _user

    private val _message = MutableLiveData<Resource<List<ChatModel>>>()
    val message: LiveData<Resource<List<ChatModel>>> get() = _message

    fun sendMessage(chatModel: ChatModel) {
        viewModelScope.launch {
            sendMessageUseCase.invoke(chatModel)
            sendPushNotificationUseCase.invoke(
                PushNotificationModel(
                    NotificationDataModel(
                        chatModel.senderId,
                        chatModel.message
                    ),"/topics/${chatModel.receiverId}"
                )
            )
        }
    }

    fun readMessage(receiverId: String) {
        viewModelScope.launch {
            _message.value = Resource.Loading
            _message.postValue(readMessageUseCase.invoke(receiverId))
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _user.value = Resource.Loading
            _user.value = getUserUseCase.invoke()
        }
    }
}
package com.example.firebase_chat_example.domain.chat_use_case

import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.ChatModel
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatModel: ChatModel){
        chatRepository.sendMessage(chatModel)
    }
}
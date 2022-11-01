package com.example.firebase_chat_example.domain.chat_use_case

import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.ChatModel
import com.example.firebase_chat_example.utils.Resource
import javax.inject.Inject

class ReadMessageUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke( receiverId: String): Resource<List<ChatModel>> {
        return chatRepository.readMessage( receiverId)
    }
}
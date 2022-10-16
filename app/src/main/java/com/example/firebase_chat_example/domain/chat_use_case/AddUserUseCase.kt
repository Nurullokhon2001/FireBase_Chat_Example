package com.example.firebase_chat_example.domain.chat_use_case

import com.example.firebase_chat_example.domain.ChatRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userName: String) {
        chatRepository.addUser( userName)
    }
}
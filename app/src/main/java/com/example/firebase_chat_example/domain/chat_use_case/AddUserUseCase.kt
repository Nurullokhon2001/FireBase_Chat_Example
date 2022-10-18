package com.example.firebase_chat_example.domain.chat_use_case

import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.UserModel
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userModel: UserModel) {
        chatRepository.addUser(userModel)
    }
}
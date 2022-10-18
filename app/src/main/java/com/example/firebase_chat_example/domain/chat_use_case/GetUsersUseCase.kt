package com.example.firebase_chat_example.domain.chat_use_case

import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(): Resource<List<UserModel>> {
        return chatRepository.getUsers()
    }
}
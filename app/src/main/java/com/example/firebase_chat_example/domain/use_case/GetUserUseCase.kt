package com.example.firebase_chat_example.domain.use_case

import com.example.firebase_chat_example.domain.AuthRepository
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke():Resource<FirebaseUser?>{
        return authRepository.getUser()
    }
}
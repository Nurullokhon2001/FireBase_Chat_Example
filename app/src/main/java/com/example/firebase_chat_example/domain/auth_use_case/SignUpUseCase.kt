package com.example.firebase_chat_example.domain.auth_use_case

import com.example.firebase_chat_example.domain.AuthRepository
import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        user:UserModel
    ): Resource<FirebaseUser> {
        return authRepository.signUp(user)
    }
}
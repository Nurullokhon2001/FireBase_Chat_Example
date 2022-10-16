package com.example.firebase_chat_example.domain.auth_use_case

import com.example.firebase_chat_example.domain.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
     operator fun invoke(){
        authRepository.logout()
    }
}
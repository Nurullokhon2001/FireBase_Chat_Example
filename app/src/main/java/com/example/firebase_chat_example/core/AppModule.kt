package com.example.firebase_chat_example.core

import com.example.firebase_chat_example.data.AuthRepositoryImpl
import com.example.firebase_chat_example.domain.AuthRepository
import com.example.firebase_chat_example.domain.use_case.GetUserUseCase
import com.example.firebase_chat_example.domain.use_case.LogoutUseCase
import com.example.firebase_chat_example.domain.use_case.SignInUseCase
import com.example.firebase_chat_example.domain.use_case.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideGetUserUseCase(authRepository: AuthRepository) = GetUserUseCase(authRepository)

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository) = LogoutUseCase(authRepository)

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository) = SignInUseCase(authRepository)

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository) = SignUpUseCase(authRepository)
}
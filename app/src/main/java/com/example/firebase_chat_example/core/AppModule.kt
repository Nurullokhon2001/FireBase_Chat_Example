package com.example.firebase_chat_example.core

import com.example.firebase_chat_example.data.AuthRepositoryImpl
import com.example.firebase_chat_example.data.ChatRepositoryImpl
import com.example.firebase_chat_example.domain.AuthRepository
import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.auth_use_case.GetUserUseCase
import com.example.firebase_chat_example.domain.auth_use_case.LogoutUseCase
import com.example.firebase_chat_example.domain.auth_use_case.SignInUseCase
import com.example.firebase_chat_example.domain.auth_use_case.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    fun provideDatabaseReference(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideChatRepository(
        firebaseAuth: FirebaseAuth,
        databaseReference: FirebaseDatabase
    ): ChatRepository =
        ChatRepositoryImpl(firebaseAuth, databaseReference)

    @Provides
    fun provideGetUserUseCase(authRepository: AuthRepository) = GetUserUseCase(authRepository)

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository) = LogoutUseCase(authRepository)

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository) = SignInUseCase(authRepository)

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository) = SignUpUseCase(authRepository)
}
package com.example.firebase_chat_example.core

import com.example.firebase_chat_example.data.AuthRepositoryImpl
import com.example.firebase_chat_example.data.ChatRepositoryImpl
import com.example.firebase_chat_example.data.NotificationApi
import com.example.firebase_chat_example.domain.AuthRepository
import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.auth_use_case.GetUserUseCase
import com.example.firebase_chat_example.domain.auth_use_case.LogoutUseCase
import com.example.firebase_chat_example.domain.auth_use_case.SignInUseCase
import com.example.firebase_chat_example.domain.auth_use_case.SignUpUseCase
import com.example.firebase_chat_example.utils.Constants.Companion.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    fun provideNotificationApi(): NotificationApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()
        .create(NotificationApi::class.java)

    @Provides
    fun provideChatRepository(
        firebaseAuth: FirebaseAuth,
        databaseReference: FirebaseDatabase,
        notificationApi: NotificationApi
    ): ChatRepository =
        ChatRepositoryImpl(firebaseAuth, databaseReference, notificationApi)

    @Provides
    fun provideGetUserUseCase(authRepository: AuthRepository) = GetUserUseCase(authRepository)

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository) = LogoutUseCase(authRepository)

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository) = SignInUseCase(authRepository)

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository) = SignUpUseCase(authRepository)
}
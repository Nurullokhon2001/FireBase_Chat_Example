package com.example.firebase_chat_example.data

import com.example.firebase_chat_example.utils.Resource
import com.example.firebase_chat_example.domain.AuthRepository
import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val current: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(user: UserModel): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signUp(
        user: UserModel
    ): Resource<FirebaseUser> {
        return try {
            val result =
                firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(user.name).build()
            )?.await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUser(): Resource<FirebaseUser?> {
        return Resource.Success(firebaseAuth.currentUser)
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
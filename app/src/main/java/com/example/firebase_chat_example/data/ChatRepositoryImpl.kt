package com.example.firebase_chat_example.data

import com.example.firebase_chat_example.domain.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    var databaseReference: FirebaseDatabase
) : ChatRepository {
    override suspend fun addUser(userName: String) {
        val hashMap: HashMap<String, String?> = HashMap()
        hashMap["userId"] = firebaseAuth.currentUser?.uid
        hashMap["userName"] = userName
        hashMap["profileImage"] = ""
       databaseReference.getReference("Users").child(firebaseAuth.currentUser!!.uid).setValue(hashMap)
    }
}
package com.example.firebase_chat_example.data

import android.util.Log
import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.ChatModel
import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private var databaseReference: FirebaseDatabase
) : ChatRepository {
    override suspend fun addUser(userModel: UserModel) {
        val hashMap: HashMap<String, String?> = HashMap()
        hashMap["userId"] = firebaseAuth.currentUser?.uid
        hashMap["userName"] = userModel.name
        hashMap["userEmail"] = userModel.email
        hashMap["userProfileImage"] = ""
        databaseReference.getReference("Users").child(firebaseAuth.currentUser!!.uid)
            .setValue(hashMap)
    }

    override suspend fun sendMessage(chatModel: ChatModel) {
        databaseReference.getReference("Chats").push()
            .setValue(chatModel)
    }

    override suspend fun readMessage(
        receiverId: String
    ): Resource<List<ChatModel>> {
        val userList = mutableListOf<ChatModel>()
        try {
            databaseReference.getReference("Chats").addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userList.clear()
                        for (dataSnapShot: DataSnapshot in snapshot.children) {
                            val user = dataSnapShot.getValue(ChatModel::class.java)

                            if (user!!.senderId == firebaseAuth.currentUser!!.uid && user.receiverId == receiverId ||
                                user.senderId == receiverId && user.receiverId == firebaseAuth.currentUser!!.uid
                            ) {
                                userList.add(user)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("onCancelled", "onCancelled: crash")
                    }
                }
            )
            return Resource.Success(userList)

        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    override suspend fun getUsers(): Resource<List<UserModel>> {
        try {
            val userList = mutableListOf<UserModel>()

            val childElements = databaseReference.getReference("Users").get().await().children
            childElements.forEach {
                if (it.child("userId").value.toString() != firebaseAuth.currentUser!!.uid) {
                    userList.add(
                        UserModel(
                            name = it.child("userName").value.toString(),
                            email = it.child("userEmail").value.toString(),
                            profileImage = it.child("userProfileImage").value.toString(),
                            uid = it.child("userId").value.toString()
                        )
                    )
                }
            }
            return Resource.Success(userList)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }
}
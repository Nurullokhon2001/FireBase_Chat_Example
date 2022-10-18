package com.example.firebase_chat_example.data

import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

    override suspend fun getUsers(): Resource<List<UserModel>> {
        try {
            val userList = mutableListOf<UserModel>()
//            databaseReference.getReference("Users").addValueEventListener(
//                object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        val currentUser = snapshot.getValue(UserModel::class.java)
//                        for (dataSnapShot: DataSnapshot in snapshot.children) {
//                            val user = dataSnapShot.getValue(UserModel::class.java)
//
//                            if (user!!.uid != currentUser!!.uid) {
//                                userList.add(user)
//                            }
//                        }
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {}
//                }
//            )
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
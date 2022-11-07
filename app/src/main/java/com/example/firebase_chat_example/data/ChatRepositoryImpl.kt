package com.example.firebase_chat_example.data

import com.example.firebase_chat_example.domain.ChatRepository
import com.example.firebase_chat_example.domain.model.ChatModel
import com.example.firebase_chat_example.domain.model.PushNotificationModel
import com.example.firebase_chat_example.domain.model.UserModel
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private var databaseReference: FirebaseDatabase,
    private val notificationApi: NotificationApi
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
        val chatList = mutableListOf<ChatModel>()
        try {
//            databaseReference.getReference("Chats").addValueEventListener(
//                object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        userList.clear()
//                        for (dataSnapShot: DataSnapshot in snapshot.children) {
//                            val user = dataSnapShot.getValue(ChatModel::class.java)
//
//                            if (user!!.senderId == firebaseAuth.currentUser!!.uid && user.receiverId == receiverId ||
//                                user.senderId == receiverId && user.receiverId == firebaseAuth.currentUser!!.uid
//                            ) {
//                                chatList.add(user)
//                            }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Log.e("onCancelled", "onCancelled: crash")
//                    }
//                }
//            )

            val childElements = databaseReference.getReference("Chats").get().await().children
            childElements.forEach {

                if (it.child("senderId").value.toString() == firebaseAuth.currentUser!!.uid && it.child(
                        "receiverId"
                    ).value.toString() == receiverId ||
                    it.child("senderId").value.toString() == receiverId && it.child("receiverId").value.toString() == firebaseAuth.currentUser!!.uid
                ) {
                    chatList.add(
                        ChatModel(
                            it.child("senderId").value.toString(),
                            it.child("receiverId").value.toString(),
                            it.child("message").value.toString(),
                            it.child("time").value.toString().toLong()

                    )
                    )
                }
            }

            return Resource.Success(chatList)

        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    override suspend fun sendPushNotification(notification: PushNotificationModel): Response<ResponseBody> {
        return notificationApi.postNotification(notification)
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
            FirebaseMessaging.getInstance()
                .subscribeToTopic("/topics/${firebaseAuth.currentUser!!.uid}")
            return Resource.Success(userList)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }
}
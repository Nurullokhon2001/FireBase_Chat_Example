package com.example.firebase_chat_example.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.domain.model.ChatModel
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter :
    ListAdapter<ChatModel, ChatAdapter.ChatViewHolder>(object : DiffUtil.ItemCallback<ChatModel>() {
        override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem.message == newItem.message && oldItem.senderId == newItem.senderId
                    && oldItem.receiverId == newItem.receiverId && oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }) {
    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtMessage: TextView = view.findViewById(R.id.tv_message)
        val txtTime: TextView = view.findViewById(R.id.tv_time)
    }
    private var firebaseUser: FirebaseUser?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return if (viewType == ANOTHER_MESSAGE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_right, parent, false)
            ChatViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_left, parent, false)
            ChatViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = getItem(position)
        holder.txtMessage.text = chat.message
        holder.txtTime.text = convertLongToTime(chat.time)
    }

    fun setUser(firebaseUser : FirebaseUser){
        this.firebaseUser = firebaseUser
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).senderId == firebaseUser!!.uid) {
            ANOTHER_MESSAGE
        } else {
            MY_MESSAGE
        }
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(DATE_FORMAT)
        return format.format(date)
    }

    companion object {
        const val DATE_FORMAT = "yyyy.MM.dd HH:mm"
        private const val MY_MESSAGE = 0
        private const val ANOTHER_MESSAGE = 1
    }
}
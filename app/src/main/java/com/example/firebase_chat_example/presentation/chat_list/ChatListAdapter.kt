package com.example.firebase_chat_example.presentation.chat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_chat_example.databinding.ItemUsersBinding
import com.example.firebase_chat_example.domain.model.UserModel

class ChatListAdapter(
    private val userOnClickListener: (String) -> Unit
) : ListAdapter<UserModel, ChatListAdapter.ViewHolder>(
    UserItemDiffCallback()
) {
    class ViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userModel: UserModel) {
            binding.userName.text = userModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { userOnClickListener.invoke(getItem(position).uid) }
    }
}

class UserItemDiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem.uid == newItem.uid

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem.uid == newItem.uid
}
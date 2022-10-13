package com.example.firebase_chat_example.presentation.chat_list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.core.BaseFragment
import com.example.firebase_chat_example.databinding.FragmentChatListBinding

class ChatListFragment : BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tv.setOnClickListener {
            findNavController().navigate(R.id.action_chatListFragment_to_registrationFragment)
        }
    }
}
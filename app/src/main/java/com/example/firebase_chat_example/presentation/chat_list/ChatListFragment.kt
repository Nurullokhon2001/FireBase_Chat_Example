package com.example.firebase_chat_example.presentation.chat_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.databinding.FragmentChatListBinding
import com.example.firebase_chat_example.databinding.FragmentRegistrationBinding

class ChatListFragment : Fragment() {

    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatListBinding.inflate(layoutInflater, container, false)

        binding.tv.setOnClickListener {
            findNavController().navigate(R.id.action_chatListFragment_to_registrationFragment)
        }

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
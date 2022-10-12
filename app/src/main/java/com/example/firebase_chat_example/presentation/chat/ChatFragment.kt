package com.example.firebase_chat_example.presentation.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.databinding.FragmentChatBinding
import com.example.firebase_chat_example.databinding.FragmentRegistrationBinding

// TODO: Rename parameter arguments, choose names that match

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
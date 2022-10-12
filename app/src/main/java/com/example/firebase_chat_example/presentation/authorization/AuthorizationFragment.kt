package com.example.firebase_chat_example.presentation.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.databinding.FragmentAuthorizationBinding
import com.example.firebase_chat_example.databinding.FragmentChatBinding

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAuthorizationBinding.inflate(layoutInflater, container, false)

        binding.btnEnter.setOnClickListener { findNavController().navigate(R.id.action_authorizationFragment_to_chatListFragment) }

        binding.tvSignUp.setOnClickListener { findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment) }

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
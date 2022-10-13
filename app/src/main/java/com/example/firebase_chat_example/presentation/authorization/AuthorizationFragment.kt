package com.example.firebase_chat_example.presentation.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.core.BaseFragment
import com.example.firebase_chat_example.databinding.FragmentAuthorizationBinding
import com.example.firebase_chat_example.databinding.FragmentChatBinding

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>(
    FragmentAuthorizationBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnEnter.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_chatListFragment)
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
    }
}
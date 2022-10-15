package com.example.firebase_chat_example.presentation.authorization

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.core.BaseFragment
import com.example.firebase_chat_example.databinding.FragmentAuthorizationBinding
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>(
    FragmentAuthorizationBinding::inflate
) {

    private val viewModel by viewModels<AuthorizationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnEnter.setOnClickListener {
            viewModel.signIn(binding.etLogin.text.toString(), binding.etPassword.text.toString())

        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
        viewModel.signIn.observe(viewLifecycleOwner) { setResult(it) }
    }

    private fun setResult(result: Resource<FirebaseUser>) {
        when (result) {
            is Resource.Failure -> {
                Toast.makeText(ctx, result.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                  findNavController().navigate(R.id.action_authorizationFragment_to_chatListFragment)
            }
            is Resource.Loading -> {

            }
        }
    }
}
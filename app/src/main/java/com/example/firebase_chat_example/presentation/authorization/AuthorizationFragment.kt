package com.example.firebase_chat_example.presentation.authorization

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.core.BaseFragment
import com.example.firebase_chat_example.databinding.FragmentAuthorizationBinding
import com.example.firebase_chat_example.domain.model.UserModel
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
            viewModel.signIn(
                UserModel(
                    email = binding.etLogin.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            )
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
                showProgress(false)
                setEnabled(true)
            }
            is Resource.Success -> {
                findNavController().navigate(R.id.action_authorizationFragment_to_chatListFragment)
                showProgress(false)
                setEnabled(true)
            }
            is Resource.Loading -> {
                showProgress(true)
                setEnabled(false)
            }
        }
    }

    private fun showProgress(show: Boolean) = with(binding) {
        progress.isVisible = show
    }

    private fun setEnabled(enable: Boolean) = with(binding) {
        etLogin.isEnabled = enable
        etPassword.isEnabled = enable
        btnEnter.isEnabled = enable
    }
}
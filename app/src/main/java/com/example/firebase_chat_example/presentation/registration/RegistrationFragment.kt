package com.example.firebase_chat_example.presentation.registration

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.core.BaseFragment
import com.example.firebase_chat_example.databinding.FragmentRegistrationBinding
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(
    FragmentRegistrationBinding::inflate
) {

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnEnter.setOnClickListener {
            viewModel.signupUser("name", "1@gmail.com", "1111111")
        }

        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
        }

        viewModel.signup.observe(viewLifecycleOwner) {
            setResults(it)
        }

    }

    private fun setResults(result : Resource<FirebaseUser>){
        when (result) {
            is Resource.Failure -> {
                Toast.makeText(ctx, result.exception.message.toString(), Toast.LENGTH_SHORT).show()
                showProgress(false)
                setEnabled(true)
            }
            is Resource.Loading ->{
                showProgress(true)
                setEnabled(false)
            }
            is Resource.Success -> {
                Toast.makeText(ctx, "Welcome", Toast.LENGTH_SHORT).show()
                showProgress(false)
                setEnabled(true)
            }
        }
    }

    private fun showProgress(show: Boolean) = with(binding) {
        progress.isVisible = show
    }

    private fun setEnabled(enable: Boolean) = with(binding) {
        etLogin.isEnabled = enable
        etPassword.isEnabled = enable
        etName.isEnabled = enable
        btnEnter.isEnabled = enable
    }
}
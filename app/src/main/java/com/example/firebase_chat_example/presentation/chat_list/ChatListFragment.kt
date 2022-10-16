package com.example.firebase_chat_example.presentation.chat_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebase_chat_example.R
import com.example.firebase_chat_example.core.BaseFragment
import com.example.firebase_chat_example.databinding.FragmentChatListBinding
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListFragment : BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::inflate) {

    private val viewModel by viewModels<ChatListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        viewModel.user.observe(viewLifecycleOwner) { setResult(it) }
        viewModel.getUser()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.logout, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.logout_menu -> {
                        viewModel.logout()
                        findNavController().navigate(R.id.action_chatListFragment_to_registrationFragment)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    

    private fun setResult(result: Resource<FirebaseUser?>) {
        when (result) {
            is Resource.Success -> {
                if (result.result == null) {
                    findNavController().navigate(R.id.action_chatListFragment_to_registrationFragment)
                }
            }
            is Resource.Failure -> {
                Toast.makeText(ctx, result.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> {}
        }
    }

}
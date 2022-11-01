package com.example.firebase_chat_example.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.firebase_chat_example.core.BaseFragment
import com.example.firebase_chat_example.databinding.FragmentChatBinding
import com.example.firebase_chat_example.domain.model.ChatModel
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>({ FragmentChatBinding.inflate(it) }) {

    private val args: ChatFragmentArgs by navArgs()

    private val adapter by lazy { ChatAdapter() }

    val viewModel by viewModels<ChatViewModel>()

    private lateinit var userReceiver: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner) {
            getReceiver(it)
        }
        viewModel.message.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val chatList = it.result
                    binding.progress.setVisibility(false)
                    adapter.submitList(chatList)
                }
                is Resource.Failure -> {
                    Toast.makeText(ctx, it.exception.message, Toast.LENGTH_SHORT).show()
                    binding.progress.setVisibility(false)
                }
                is Resource.Loading -> {
                    binding.progress.setVisibility(true)
                }
            }
        }
        viewModel.readMessage(args.userUuid)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSendMessage.setOnClickListener {
            viewModel.sendMessage(createChatModel())
            binding.etMessage.setText("")
        }
        binding.chatRecyclerView.adapter = adapter
    }

    private fun createChatModel(): ChatModel {
        return ChatModel(
            userReceiver.uid,
            args.userUuid,
            binding.etMessage.text.toString(),
            Calendar.getInstance().time.time
        )
    }

    private fun getReceiver(result: Resource<FirebaseUser?>) {
        when (result) {
            is Resource.Success -> {
                adapter.setUser(result.result!!)
            }
            is Resource.Failure -> {
                Toast.makeText(ctx, result.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> {}
        }
    }

    private fun ProgressBar.setVisibility(isVisible: Boolean) {
        this.isVisible = isVisible
    }
}
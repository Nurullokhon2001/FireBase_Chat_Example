package com.example.firebase_chat_example.presentation.chat_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_chat_example.domain.use_case.GetUserUseCase
import com.example.firebase_chat_example.domain.use_case.LogoutUseCase
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _user = MutableLiveData<Resource<FirebaseUser?>>()
     val user: LiveData<Resource<FirebaseUser?>> get() = _user

    fun getUser() {
        viewModelScope.launch {
            _user.value = Resource.Loading
            _user.value = getUserUseCase.invoke()
        }
    }

    fun logout(){
            logoutUseCase.invoke()
    }
}
package com.example.firebase_chat_example.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_chat_example.domain.use_case.SignInUseCase
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _signIn = MutableLiveData<Resource<FirebaseUser>>()
    val signIn: LiveData<Resource<FirebaseUser>> get() = _signIn

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signIn.value = Resource.Loading
            val result = signInUseCase.invoke(email, password)
            _signIn.value = result
        }
    }
}
package com.example.firebase_chat_example.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_chat_example.domain.use_case.SignUpUseCase
import com.example.firebase_chat_example.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signup = MutableLiveData<Resource<FirebaseUser>>()
    val signup: LiveData<Resource<FirebaseUser>> get() = _signup

    fun signupUser(name: String, email: String, password: String) = viewModelScope.launch {
        _signup.value = Resource.Loading
        val result = signUpUseCase.invoke(name, email, password)
        _signup.value = result
    }
}
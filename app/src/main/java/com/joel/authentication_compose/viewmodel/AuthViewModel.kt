package com.joel.authentication_compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.authentication_compose.auth.AuthRepoImplementation
import com.joel.authentication_compose.auth.AuthResult
import com.joel.authentication_compose.auth.AuthState
import com.joel.authentication_compose.auth.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository : AuthRepoImplementation
): ViewModel() {

    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    var authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvents(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.IsUserNameChangedLogin -> {
                state = state.copy(isUserNameChangedLogIn = event.userName)

            }

            is AuthUiEvent.IsPasswordChangedLogin -> {
                state = state.copy(isPasswordChangedLogIn = event.password)

            }
            is AuthUiEvent.Login -> {
                login()
            }

            is AuthUiEvent.IsUserNameChangedSignIn -> {
                state = state.copy(isUserNameChangedSignIn = event.userName)

            }
            is AuthUiEvent.IsEmailChangedSignIn -> {
                state = state.copy(isEmailChangedSignIn = event.email)

            }
            is AuthUiEvent.IsPhoneNumberChangedSignIn -> {
                state = state.copy(isPhoneNumberChangedSignIn = event.phoneNumber)

            }
            is AuthUiEvent.IsLocationChangedSignIn -> {
                state = state.copy(isLocationChangedSignIn = event.location)

            }
            is AuthUiEvent.IsPasswordChangedSignIn -> {
                state = state.copy(isPasswordChangedLogIn = event.password)

            }
            is AuthUiEvent.SignIn -> {
                register()
            }
        }
    }
    private fun register(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
          val results = repository.register(
                userName = state.isUserNameChangedSignIn,
                email = state.isEmailChangedSignIn,
                phoneNumber = state.isPhoneNumberChangedSignIn,
                location = state.isLocationChangedSignIn,
                password = state.isPasswordChangedSignIn
            )
            resultChannel.send(results)
            state = state.copy(isLoading = false)
        }
    }

    private fun login(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result =  repository.login(
                userName = state.isUserNameChangedLogIn,
                password = state.isPasswordChangedLogIn
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}


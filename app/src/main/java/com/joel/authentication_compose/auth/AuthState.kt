package com.joel.authentication_compose.auth

data class AuthState(
    val isUserNameChangedLogIn: String = "",
    val isPasswordChangedLogIn: String = "",

    val isUserNameChangedSignIn: String = "",
    val isEmailChangedSignIn: String = "",
    val isPhoneNumberChangedSignIn: String = "",
    val isLocationChangedSignIn: String = "",
    val isPasswordChangedSignIn: String = "",
    val isLoading : Boolean = false

)

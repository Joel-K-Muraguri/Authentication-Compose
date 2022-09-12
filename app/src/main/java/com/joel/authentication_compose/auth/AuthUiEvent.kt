package com.joel.authentication_compose.auth


sealed class AuthUiEvent{

    data class IsUserNameChangedLogin(val userName: String) : AuthUiEvent()
    data class IsPasswordChangedLogin(val password: String) : AuthUiEvent()
    object  Login : AuthUiEvent()


    data class IsUserNameChangedSignIn(val userName: String) : AuthUiEvent()
    data class IsEmailChangedSignIn(val email: String) : AuthUiEvent()
    data class IsPhoneNumberChangedSignIn(val phoneNumber: String) : AuthUiEvent()
    data class IsLocationChangedSignIn(val location: String) : AuthUiEvent()
    data class IsPasswordChangedSignIn(val password: String) : AuthUiEvent()
    object  SignIn : AuthUiEvent()

}

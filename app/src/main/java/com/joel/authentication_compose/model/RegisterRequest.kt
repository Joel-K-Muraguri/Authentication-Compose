package com.joel.authentication_compose.model

data class RegisterRequest(
    val location: String,
    val email: String,
    val number: String,
    val name: String,
    val password : String,
)

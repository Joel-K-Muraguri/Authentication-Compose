package com.joel.authentication_compose.model

data class RegisterRequest(
    val address: String,
    val email: String,
    val phone_number: String,
    val username: String,
    val password : String,
)

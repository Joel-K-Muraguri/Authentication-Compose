package com.joel.authentication_compose.model

data class TokenResponse(
    val address: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone_number: String,
    val token: String,
    val username: String
)
package com.joel.authentication_compose.auth

interface AuthRepo {

    suspend fun login(userName: String, password: String) : AuthResult<Unit>
    suspend fun register(
        userName: String, email: String, phoneNumber: String, location: String, password: String) : AuthResult<Unit>

}
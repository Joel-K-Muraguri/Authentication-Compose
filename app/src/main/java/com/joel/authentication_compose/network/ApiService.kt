package com.joel.authentication_compose.network

import com.joel.authentication_compose.model.LogInRequest
import com.joel.authentication_compose.model.RegisterRequest
import com.joel.authentication_compose.model.TokenResponse
import com.joel.authentication_compose.utils.ApiConstants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST(ApiConstants.REGISTER_ENDPOINT)
    suspend fun register(
        @Body request: RegisterRequest
    ) : TokenResponse


    @POST(ApiConstants.LOG_IN_ENDPOINT)
    suspend fun login(
        @Body request: LogInRequest
    ) : TokenResponse

    @GET(ApiConstants.AUTHENTICATE_ENDPOINT)
    suspend fun authenticate(
        @Header("Authorization") token : String
    ) : TokenResponse


}
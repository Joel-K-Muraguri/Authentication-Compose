package com.joel.authentication_compose.network

import com.joel.authentication_compose.utils.ApiConstants
import com.joel.authentication_compose.model.LogInRequest
import com.joel.authentication_compose.model.RegisterRequest
import com.joel.authentication_compose.model.TokenResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
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


    companion object{
       private var apiService : ApiService? = null
        fun getInstance() : ApiService {
            if (apiService == null){
                return Retrofit.Builder()
                    .baseUrl(ApiConstants.MAIN_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
                return apiService!!

        }
    }
}
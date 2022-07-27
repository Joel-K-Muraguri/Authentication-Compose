package com.joel.authentication_compose.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST(ApiConstants.REGISTER_ENDPOINT)
    fun register(
        @Body request: RegisterRequest
    ) : Call<TokenResponse>


    @POST(ApiConstants.LOG_IN_ENDPOINT)
     fun login(
        @Body request: LogInRequest
    ) : Call<TokenResponse>


    companion object{
       private var apiService : ApiService ? = null
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
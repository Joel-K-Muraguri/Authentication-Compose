package com.joel.authentication_compose.auth

import android.content.SharedPreferences
import androidx.navigation.NavHostController
import com.joel.authentication_compose.model.LogInRequest
import com.joel.authentication_compose.model.RegisterRequest
import com.joel.authentication_compose.network.ApiService
import com.joel.authentication_compose.utils.ApiConstants
import retrofit2.HttpException

class AuthRepoImplementation(
    private val apiService: ApiService,
    private val prefs: SharedPreferences,

) : AuthRepo {
    override suspend fun login(userName: String, password: String): AuthResult<Unit> {
        return try {
            apiService.login(
                request = LogInRequest(
                    username = userName,
                    password = password
                )
            )
            login(userName, password)
        }
        catch (e: HttpException){
            if (e.code() == 401){
               AuthResult.Unauthorized()
            }
            else
                AuthResult.UnknownError()
        }
        catch (e : Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun register(
        userName: String,
        email: String,
        phoneNumber: String,
        location: String,
        password: String,
    ): AuthResult<Unit> {
       return try {
           val response =  apiService.register(
               request = RegisterRequest(
                   location = location,
                   email = email,
                   number = phoneNumber,
                   name = userName,
                   password = password
               )
           )
          prefs.edit()
              .putString(ApiConstants.USER_TOKEN, response.token)
              .apply()
           AuthResult.Authorized()
       }
       catch (e : HttpException){
           if (e.code() == 401){
               AuthResult.Unauthorized()
           }
           else
               AuthResult.UnknownError()
       }
        catch (e: Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try{

            val token = prefs.getString(ApiConstants.USER_TOKEN, "") ?: return AuthResult.Unauthorized()
            apiService.authenticate("Token $token")
            AuthResult.Authorized()
        }
        catch (e : HttpException){
            if (e.code() == 401){
                AuthResult.Unauthorized()
            }
            else
                AuthResult.UnknownError()
        }
        catch (e : Exception){
            AuthResult.UnknownError()
        }
    }
}
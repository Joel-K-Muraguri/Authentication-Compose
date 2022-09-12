package com.joel.authentication_compose.network

import android.content.Context
import android.content.SharedPreferences
import com.joel.authentication_compose.R
import com.joel.authentication_compose.utils.ApiConstants

class SessionManager(
    context: Context
) {
    private val prefs : SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    //In this class we are going to save the authentication token and fetch the token


    fun saveAuthToken(token: String){
        val editor = prefs.edit()
        editor.putString(ApiConstants.USER_TOKEN, token)
        editor.apply()
    }

    //we are using the fetch token to display user token on the screen
    fun fetchAuthToken() : String? {
        return prefs.getString(ApiConstants.USER_TOKEN, "")

    }
}
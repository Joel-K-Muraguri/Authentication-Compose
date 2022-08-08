package com.joel.authentication_compose.network

import android.content.Context
import android.content.SharedPreferences
import com.joel.authentication_compose.R
import com.joel.authentication_compose.utils.ApiConstants

class SessionManager(
    context: Context
) {
    private val prefs : SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveAuthToken(token : String){
        val editor = prefs.edit()
        editor.putString(ApiConstants.USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String?{
        return prefs.getString(ApiConstants.USER_TOKEN, " ")
    }

    fun checkStatus() {

    }

}
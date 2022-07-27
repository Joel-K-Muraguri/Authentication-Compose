package com.joel.authentication_compose.model

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.joel.authentication_compose.view.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {


     fun register(context: Context, navController: NavHostController, registerRequest: RegisterRequest){
        val apiService = ApiService.getInstance()
        val sessionManager = SessionManager(context)
        apiService.register(registerRequest).enqueue(object : Callback<TokenResponse>{
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                if (response.code() == 200){
                    Toast.makeText(context, " Successful", Toast.LENGTH_SHORT).show()
                    val userdata = response.body()
                    sessionManager.saveAuthToken(userdata!!.token)

                }
                else if (response.code() == 401) {
                    Toast.makeText(context,"Already Existing Credentials" ,Toast.LENGTH_SHORT).show()

                }
                else
                    Toast.makeText(context,"Something Went Wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Toast.makeText(context,"Unauthorized", Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun login(context: Context, navController: NavHostController, logInRequest: LogInRequest){

        val apiService = ApiService.getInstance()
        val sessionManager = SessionManager(context)

        apiService.login(logInRequest).enqueue(object : Callback<TokenResponse>{
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.code() == 200){
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    val userData = response.body()
                    sessionManager.saveAuthToken(userData!!.token)
                    navController.navigate(Routes.DETAILS_SCREEN)
                }
                else if (response.code() == 401){
                    Toast.makeText(context, "Incorrect credentials", Toast.LENGTH_SHORT).show()

                }
                else
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Toast.makeText(context, "Unauthorized access", Toast.LENGTH_SHORT).show()
            }

        })

    }

}
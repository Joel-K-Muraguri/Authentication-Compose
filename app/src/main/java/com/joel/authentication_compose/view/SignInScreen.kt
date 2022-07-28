package com.joel.authentication_compose.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joel.authentication_compose.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignInScreen(
    navController: NavHostController,
    context: Context

){
    var userName by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var location by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        OutlinedTextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            label = {
                Text(text = "UserName")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },
            label = {
                Text(text = "Phone Number")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = location,
            onValueChange = {
                location = it
            },
            label = {
                Text(text = "Location")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password")
            },
            shape = RoundedCornerShape(20.dp)
        )
        Button(
            onClick = {
//                      navController.navigate(Routes.DETAILS_SCREEN)
                  register(context,
                      RegisterRequest(location,email,phoneNumber,userName,password), navController)
            },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "Sign In")
        }
    }
}

fun register(context: Context, registerRequest: RegisterRequest, navController: NavHostController){
    val apiService = ApiService.getInstance()
    val sessionManager = SessionManager(context)

    Toast.makeText(context, "Signing in...", Toast.LENGTH_SHORT).show()
    apiService.register(registerRequest).enqueue(object : Callback<TokenResponse>{
        override fun onResponse(
            call: Call<TokenResponse>,
            response: Response<TokenResponse>)
        {
           if (response.code() == 200 && response.body() != null){
               //Successful Registration
               Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
               val userData = response.body()
               sessionManager.saveAuthToken(userData!!.token)
               navController.navigate(Routes.DETAILS_SCREEN)
           }
            else if (response.code() == 401){
               Log.d("TEST::", "onResponse: "+response.message())
                //Already existing credentials
                Toast.makeText(context, "Existing Credentials", Toast.LENGTH_SHORT).show()
           }
            else if(response.code() == 403){
                Toast.makeText(context,"Forbidden", Toast.LENGTH_SHORT).show()
           }
            else
                //Something went wrong
               Log.d("TEST::", "onResponse: "+response.message())
                Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show()

        }

        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            Log.d("TEST::", "onResponse: "+ t.message)
     //       Toast.makeText(context, "Unauthorized Access", Toast.LENGTH_SHORT).show()
        }

    })
}
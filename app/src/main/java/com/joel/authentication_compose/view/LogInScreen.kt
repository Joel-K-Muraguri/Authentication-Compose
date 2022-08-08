package com.joel.authentication_compose.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joel.authentication_compose.auth.AuthUiEvent
import com.joel.authentication_compose.model.*
import com.joel.authentication_compose.network.ApiService
import com.joel.authentication_compose.network.SessionManager
import com.joel.authentication_compose.viewmodel.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LogInScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
){
    var state = authViewModel.state

    val context = LocalContext.current
    val navController = rememberNavController()
    var userName by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
            OutlinedTextField(
                value = state.isUserNameChanged,
                onValueChange = {
                                authViewModel.onEvents(AuthUiEvent.IsUserNameChangedLogin(it))
                },
                label = {
                    Text(text = "UserName")
                },
                shape = RoundedCornerShape(20.dp)
            )
        OutlinedTextField(
            value = state.isPasswordChanged,
            onValueChange = {
                authViewModel.onEvents(AuthUiEvent.IsPasswordChangedLogin(it))
            },
            label = {
                Text(text = "Password")
            },
            shape = RoundedCornerShape(20.dp)
        )
        Button(
            onClick = {
                 //  login(context,navController, LogInRequest(userName, password))
                authViewModel.onEvents(AuthUiEvent.Login)
            },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "Log In")
        }

        if (state.isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White))
            {
                CircularProgressIndicator()
            }
        }
    }
}

suspend fun login(context: Context, navController: NavHostController, logInRequest: LogInRequest){
    val apiService = ApiService.getInstance()
    val sessionManager = SessionManager(context)
    Toast.makeText(context, "Logging in", Toast.LENGTH_SHORT).show()
    apiService.login(logInRequest).enqueue(object : Callback<TokenResponse>{
        override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
            if (response.code() == 200){
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                val userData = response.body()
                sessionManager.saveAuthToken(userData!!.token)
                navController.navigate(Routes.DETAILS_SCREEN)
            }
            else if( response.code() == 401){
                Log.d("TEST::", "onResponse: "+response.message())
                Toast.makeText(context, "Wrong Credentials ", Toast.LENGTH_SHORT).show()
            }
            else if(response.code() == 403){
                Toast.makeText(context,"Forbidden", Toast.LENGTH_SHORT).show()
            }
            else
                Log.d("TEST::", "onResponse: "+response.message())
                Toast.makeText(context, " Something Went Wrong", Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            Log.d("TEST::", "onResponse: "+t.message)
       //     Toast.makeText(context," Unauthorized Access", Toast.LENGTH_SHORT).show()

        }

    })
}

@Preview(showBackground = true)
@Composable
fun LogInScreenPreview(){
    val navController = rememberNavController()

}
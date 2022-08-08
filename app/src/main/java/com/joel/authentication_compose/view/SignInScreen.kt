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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joel.authentication_compose.auth.AuthResult
import com.joel.authentication_compose.auth.AuthUiEvent
import com.joel.authentication_compose.model.*
import com.joel.authentication_compose.network.ApiService
import com.joel.authentication_compose.network.SessionManager
import com.joel.authentication_compose.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = viewModel(),
){

    val navController = rememberNavController()
    val context = LocalContext.current
    val state = authViewModel.state

    LaunchedEffect(authViewModel,context){
       authViewModel.authResults.collect{ result ->
           when(result){
               is AuthResult.Authorized -> {
                   Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()

               }
               is AuthResult.Unauthorized -> {

               }
               is AuthResult.UnknownError -> {

               }
           }
       }
    }



    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        OutlinedTextField(
            value = state.isUserNameChangedSignIn,
            onValueChange = {
               authViewModel.onEvents(AuthUiEvent.IsUserNameChangedSignIn(it))
            },
            label = {
                Text(text = "UserName")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = state.isPhoneNumberChangedSignIn,
            onValueChange = {
                authViewModel.onEvents(AuthUiEvent.IsPhoneNumberChangedSignIn(it))
            },
            label = {
                Text(text = "Phone Number")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = state.isEmailChangedSignIn,
            onValueChange = {
                authViewModel.onEvents(AuthUiEvent.IsEmailChangedSignIn(it))
            },
            label = {
                Text(text = "Email")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = state.isLocationChangedSignIn,
            onValueChange = {
             authViewModel.onEvents(AuthUiEvent.IsLocationChangedSignIn(it))
            },
            label = {
                Text(text = "Location")
            },
            shape = RoundedCornerShape(20.dp)
        )
        OutlinedTextField(
            value = state.isPasswordChangedSignIn,
            onValueChange = {
                authViewModel.onEvents(AuthUiEvent.IsPasswordChangedSignIn(it))
            },
            label = {
                Text(text = "Password")
            },
            shape = RoundedCornerShape(20.dp)
        )
        Button(
            onClick = {
//                      navController.navigate(Routes.DETAILS_SCREEN)
                 // register(context, RegisterRequest(location,email,phoneNumber,userName,password), navController)
                authViewModel.onEvents(AuthUiEvent.SignIn)

            },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "Sign In")
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

//suspend fun register(context: Context, registerRequest: RegisterRequest, navController: NavHostController){
//    val apiService = ApiService.getInstance()
//    val sessionManager = SessionManager(context)
//
//    Toast.makeText(context, "Signing in...", Toast.LENGTH_SHORT).show()
//    apiService.register(registerRequest).enqueue(object : Callback<TokenResponse>{
//        override fun onResponse(
//            call: Call<TokenResponse>,
//            response: Response<TokenResponse>)
//        {
//           if (response.code() == 200 && response.body() != null){
//               //Successful Registration
//               Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
//               val userData = response.body()
//               sessionManager.saveAuthToken(userData!!.token)
//               navController.navigate(Routes.DETAILS_SCREEN)
//           }
//            else if (response.code() == 401){
//               Log.d("TEST::", "onResponse: "+response.message())
//                //Already existing credentials
//                Toast.makeText(context, "Existing Credentials", Toast.LENGTH_SHORT).show()
//           }
//            else if(response.code() == 403){
//                Toast.makeText(context,"Forbidden", Toast.LENGTH_SHORT).show()
//           }
//            else
//                //Something went wrong
//               Log.d("TEST::", "onResponse: "+response.message())
//                Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show()
//
//        }
//
//        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
//            Log.d("TEST::", "onResponse: "+ t.message)
//     //       Toast.makeText(context, "Unauthorized Access", Toast.LENGTH_SHORT).show()
//        }
//
//    })
//}
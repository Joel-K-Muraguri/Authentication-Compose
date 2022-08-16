package com.joel.authentication_compose.view

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
import androidx.navigation.compose.rememberNavController
import com.joel.authentication_compose.auth.AuthResult
import com.joel.authentication_compose.auth.AuthUiEvent
import com.joel.authentication_compose.view.destinations.AuthenticationScreenDestination
import com.joel.authentication_compose.view.destinations.DetailsScreenDestination
import com.joel.authentication_compose.viewmodel.AuthViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo


@Destination
@Composable
fun LogInScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
){
    val state = authViewModel.state

    val context = LocalContext.current
    val navController = rememberNavController()

    LaunchedEffect(authViewModel,context){
        authViewModel.authResults.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    Toast.makeText(context," Successful", Toast.LENGTH_LONG).show()
                    navigator.navigate(DetailsScreenDestination){
                        popUpTo(AuthenticationScreenDestination)
                    }

                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context,"You are not Authorized", Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context," An Unknown Error Occurred", Toast.LENGTH_LONG).show()
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
                value = state.isUserNameChangedLogIn,
                onValueChange = {
                                authViewModel.onEvents(AuthUiEvent.IsUserNameChangedLogin(it))
                },
                label = {
                    Text(text = "UserName")
                },
                shape = RoundedCornerShape(20.dp)
            )
        OutlinedTextField(
            value = state.isPasswordChangedLogIn,
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



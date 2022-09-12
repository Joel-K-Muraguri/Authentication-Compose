package com.joel.authentication_compose.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joel.authentication_compose.auth.AuthResult
import com.joel.authentication_compose.auth.AuthUiEvent
import com.joel.authentication_compose.view.destinations.AuthenticationScreenDestination
import com.joel.authentication_compose.view.destinations.DetailsScreenDestination
import com.joel.authentication_compose.view.destinations.SignInScreenDestination
import com.joel.authentication_compose.viewmodel.AuthViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun LogInScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
){
    val state = authViewModel.state

    val context = LocalContext.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(authViewModel,context){
        authViewModel.authResults.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    Toast.makeText(context," Successful", Toast.LENGTH_LONG).show()
                    navigator.navigate(DetailsScreenDestination){
                        popUpTo(AuthenticationScreenDestination.route){
                            inclusive = true
                        }
                    }

                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context,"You are not Authorized", Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context,"Check your Internet Connection", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold(
        backgroundColor = Color.Blue
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {

           Text(
               text = "LOG IN",
               modifier = Modifier
                   .weight(1f),
               style = MaterialTheme.typography.h4,
               textAlign = TextAlign.Center

           )

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(3f),
                shape = RoundedCornerShape(32.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxSize(),
                ) {
                    Text(
                        text = "Welcome Back!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = state.isUserNameChangedLogIn,
                            onValueChange = {
                               authViewModel.onEvents(AuthUiEvent.IsUserNameChangedLogin(it))
                            },
                            singleLine = true,

                            label = {
                                Text(text = "UserName")
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = state.isPasswordChangedLogIn,
                            onValueChange ={
                                authViewModel.onEvents(AuthUiEvent.IsPasswordChangedLogin(it))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text(text = "Password")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done),
                            visualTransformation = if(isPasswordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (isPasswordVisible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                                val description = if (isPasswordVisible) "Hide password" else "Show password"

                                IconButton(onClick = { isPasswordVisible =! isPasswordVisible }) {
                                    Icon(
                                        imageVector = image,
                                        contentDescription = description
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(contentAlignment = Alignment.TopStart) {
                            Text(
                                text = "Password should be at least 8 characters",
                                style = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                     authViewModel.onEvents(AuthUiEvent.Login)
                            },

                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Log in")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(
                                    text = "Forgot Password?",
                                    color = Color.Blue,
                                    style = MaterialTheme.typography.caption
                                )
                            }
                            TextButton(onClick = { navigator.navigate(SignInScreenDestination) }) {
                                Text(
                                    text = "Sign In",
                                    color = Color.Blue,
                                    style = MaterialTheme.typography.h6
                                )
                            }
                        }
                    }
                }

            }
        }
    }





            if (state.isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                )
                {
                    CircularProgressIndicator()
                }

        }

}



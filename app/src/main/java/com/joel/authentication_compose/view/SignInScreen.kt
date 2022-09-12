package com.joel.authentication_compose.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joel.authentication_compose.auth.AuthResult
import com.joel.authentication_compose.auth.AuthUiEvent
import com.joel.authentication_compose.network.SessionManager
import com.joel.authentication_compose.view.destinations.AuthenticationScreenDestination
import com.joel.authentication_compose.view.destinations.DetailsScreenDestination
import com.joel.authentication_compose.view.destinations.LogInScreenDestination
import com.joel.authentication_compose.viewmodel.AuthViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination
@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
){

    val context = LocalContext.current
    val state = authViewModel.state


    var isPasswordVisible by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(authViewModel,context){
       authViewModel.authResults.collect{ result ->
           when(result){
               is AuthResult.Authorized -> {
                   Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                   navigator.navigate(DetailsScreenDestination){
                       popUpTo(AuthenticationScreenDestination)
                   }
               }
               is AuthResult.Unauthorized -> {
                   Toast.makeText(context, "You are not Authorized", Toast.LENGTH_SHORT).show()

               }
               is AuthResult.UnknownError -> {
                   Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show()
               }
           }
       }
    }

    Column(
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

    ) {


                Text(
                    text = "JOIN",
                    style = MaterialTheme.typography.h4,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.weight(1f)

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
                        text = "Welcome To My Auth App",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = state.isUserNameChangedSignIn,
                            onValueChange = {
                                authViewModel.onEvents(AuthUiEvent.IsUserNameChangedSignIn(it))
                            },
                            singleLine = true,

                            label = {
                                Text(text = "UserName")
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        OutlinedTextField(
                            value = state.isPhoneNumberChangedSignIn,
                            onValueChange = {
                                authViewModel.onEvents(AuthUiEvent.IsPhoneNumberChangedSignIn(it))
                            },
                            singleLine = true,
                            label = {
                                Text(text = "Phone Number")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        OutlinedTextField(
                            value = state.isEmailChangedSignIn,
                            onValueChange = {
                               authViewModel.onEvents(AuthUiEvent.IsEmailChangedSignIn(it))
                            },
                            singleLine = true,

                            label = {
                                Text(text = "Email")
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        OutlinedTextField(
                            value = state.isLocationChangedSignIn,
                            onValueChange = {
                               authViewModel.onEvents(AuthUiEvent.IsLocationChangedSignIn(it))
                            },
                            singleLine = true,
                            label = {
                                Text(text = "Location")
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        OutlinedTextField(
                            value = state.isPasswordChangedSignIn,
                            onValueChange = {
                               authViewModel.onEvents(AuthUiEvent.IsPasswordChangedSignIn(it))
                            },
                            singleLine = true,
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
                                           },
                                label = {
                                Text(text = "Password")
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Box(contentAlignment = Alignment.TopStart) {
                            Text(
                                text = "Password should be at least 8 characters",
                                style = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                authViewModel.onEvents(AuthUiEvent.SignIn)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Sign in")
                        }

                        TextButton(onClick = { navigator.navigate(LogInScreenDestination) }) {
                            Text(
                                text = "Already have an Account?",
                                color = Color.Blue
                            )
                        }
                    }

            }
        }
    }



        if (state.isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator()
            }
    }
}



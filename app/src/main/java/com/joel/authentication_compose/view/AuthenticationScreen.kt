package com.joel.authentication_compose.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joel.authentication_compose.view.destinations.LogInScreenDestination
import com.joel.authentication_compose.view.destinations.SignInScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun AuthenticationScreen(
    navigator: DestinationsNavigator
){

    Column(

        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = " Hi This is Authentication App",
            style = MaterialTheme.typography.h3
        )
        Button(onClick = {
            navigator.navigate(LogInScreenDestination)

        }) {
            Text(text = "Log In")
        }
        Button(onClick = {
            navigator.navigate(SignInScreenDestination)

        }) {
            Text(text = "Register")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AuthenticationScreenPreview(){
//    val navController = rememberNavController()
//    AuthenticationScreen(navController)
//}
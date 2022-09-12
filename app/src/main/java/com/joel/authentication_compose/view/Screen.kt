package com.joel.authentication_compose.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.authentication_compose.network.SessionManager
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailsScreen(
    navigator: DestinationsNavigator
){
    val context = LocalContext.current
    val token = SessionManager(context).fetchAuthToken()
    Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
    Column(
        modifier = Modifier.
            fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hi , You are authenticated",
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "LOG OUT")
        }

    }
}


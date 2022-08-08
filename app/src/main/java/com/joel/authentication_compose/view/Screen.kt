package com.joel.authentication_compose.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.joel.authentication_compose.network.SessionManager

@Composable
fun DetailsScreen(){
    val context = LocalContext.current
    val name: String?
    name = "Authentication App Demo"

    val token = SessionManager(context).fetchAuthToken()
    Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        
        Text(
            text = "You are authenticated",
            style = MaterialTheme.typography.h5
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Log out")
        }
    }
}


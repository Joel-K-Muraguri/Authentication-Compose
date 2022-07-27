package com.joel.authentication_compose.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.joel.authentication_compose.model.SessionManager

@Composable
fun DetailsScreen(context: Context){
    val name: String?
    name = "Authentication App Demo"

    val token = SessionManager(context).fetchAuthToken()
    Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        Text(
            text = "Hello This is $name",
            style = MaterialTheme.typography.h3
        )
    }
}


package com.jm.jetpackcompose_crud.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun TopBar(screenName: String, navController: NavController, destination: String) {
    TopAppBar(title = { Text(text = screenName, color = Color.White) },
        backgroundColor = Color.Blue,
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(destination) {
                    popUpTo(navController.graph.startDestinationId)
                }
            }) {
                Icon(Icons.Default.ArrowBack, "", tint = Color.White)

            }
        })
}
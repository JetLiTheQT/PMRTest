package com.example.pmrtest


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
// Dummy route for main screen
fun HomeScreen(navController: NavHostController) { }

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val sharedViewModel = viewModel<SharedViewModel>()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        topBar = { TopNavBar(navController = navController, sharedViewModel = sharedViewModel) },
        content = {innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = "Hey There this is the home screen!")
                }
                AppNavigator(navController)

            }
        }
    )
}


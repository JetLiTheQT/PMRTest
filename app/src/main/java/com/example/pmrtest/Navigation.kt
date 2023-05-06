package com.example.pmrtest

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Log : Screen("log")
}

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Log.route) { LogScreen(navController) }

    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TopNavBar (navController: NavHostController, sharedViewModel: SharedViewModel) {
    val canGoBack = navController.previousBackStackEntry != null
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    var title by remember { mutableStateOf("") }

    sharedViewModel.appBarTitle.observeForever() {
        Log.d("TAG", "TopNavBar: $it")
        title = when (navController.currentBackStackEntry?.destination?.route) {
            Screen.Home.route -> "PMR"
            Screen.Log.route -> "Log"
            else -> "Noteworthy"
        }
    }

    TopAppBar(
        title = { Text(title) },
        navigationIcon = if (canGoBack) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else if (!canGoBack) {{
            IconButton(onClick = { navController.navigate(Screen.Log.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            } }){
                Icon(painter = painterResource(id = R.drawable.notification_icon), contentDescription = "Icon")
            }
        }}
        else null,
        actions = {

        }
    )
}

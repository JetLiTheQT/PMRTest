package com.example.pmrtest

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.finalprojectteam11.noteworthy.ui.ContactScreen
import kotlinx.coroutines.launch


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Log : Screen("log")
    object Contact : Screen("contact")
}

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Log.route) { LogScreen(navController) }
        composable(Screen.Contact.route) { ContactScreen(navController) }

    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TopNavBar (navController: NavHostController, sharedViewModel: SharedViewModel, onMoreIconClick: (() -> Unit)? = null) {
    val canGoBack = navController.previousBackStackEntry != null
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    var title by remember { mutableStateOf("") }

    sharedViewModel.appBarTitle.observeForever() {
        Log.d("TAG", "TopNavBar: $it")
        title = when (navController.currentBackStackEntry?.destination?.route) {
            Screen.Home.route -> "PMR"
            Screen.Log.route -> "Log"
            Screen.Contact.route -> "Contact Us"
            else -> "PMR"
        }
    }

    TopAppBar(
        title = { Text(title) },
        navigationIcon = if (canGoBack) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }}else if (currentRoute == Screen.Home.route) {
                {
                    IconButton(onClick = { onMoreIconClick?.invoke() }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More")
                    }
                }
            } else null,
        actions = {
            if (!canGoBack) {
                IconButton(onClick = { navController.navigate(Screen.Log.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                } }) {
                    Icon(painter = painterResource(id = R.drawable.notification_icon), contentDescription = "Icon")
                }
            }
        }
    )

}

data class DrawerItem(val label: String, val icon: ImageVector, val route: String)
@Composable
fun NavigationDrawer(navController: NavHostController, onLogClick: (() -> Unit)? = null, onContactClick: (() -> Unit)? = null) {
    val drawerItems = listOf(
        DrawerItem("Home", Icons.Default.Home, Screen.Home.route),
        DrawerItem("Log", Icons.Default.List, Screen.Log.route),
        DrawerItem("Contact Us", Icons.Default.Phone, Screen.Contact.route),

        // Add more items here
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .width(160.dp)
    ) {
        drawerItems.forEach { item ->
            DrawerListItem(item) {
                when (item.route) {
                    Screen.Log.route -> onLogClick?.invoke() ?: defaultNavigation(item.route, navController)
                    Screen.Contact.route -> onContactClick?.invoke() ?: defaultNavigation(item.route, navController)
                    else -> defaultNavigation(item.route, navController)
                }
            }
        }
    }
}
private fun defaultNavigation(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
@Composable
fun DrawerListItem(drawerItem: DrawerItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment =  Alignment.CenterVertically
    ) {
        Icon(drawerItem.icon, contentDescription = drawerItem.label, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = drawerItem.label, style = MaterialTheme.typography.subtitle1)
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopNavBarWithDrawer(navController: NavHostController, sharedViewModel: SharedViewModel, content: @Composable () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopNavBar(navController, sharedViewModel, onMoreIconClick = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        drawerContent = {
            NavigationDrawer(navController,
                onLogClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                        navController.navigate(Screen.Log.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                onContactClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                        navController.navigate(Screen.Contact.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    ) {
        content()
    }
}






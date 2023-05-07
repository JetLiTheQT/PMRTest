package com.finalprojectteam11.noteworthy.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactScreen(navController: NavHostController) {
    val contactList = listOf(
        Contact(name = "Ekkachai Jet Ittihrit - UI/UX, Android Development",email = "ittihrie@oregonstate.edu"),
        Contact(name = "Jack Schofield",email = "schofija@oregonstate.edu")
    )

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                LazyColumn {
                    itemsIndexed(contactList) { index, contact ->
                        val gradient = remember {
                            when (index % 3) {
                                0 -> Brush.linearGradient(
                                    colors = listOf(
                                        Color.Blue.copy(alpha = 0.6f),
                                        Color.Green.copy(alpha = 0.6f)
                                    )
                                )
                                1 -> Brush.linearGradient(
                                    colors = listOf(
                                        Color.Magenta.copy(alpha = 0.6f),
                                        Color.Yellow.copy(alpha = 0.6f)
                                    )
                                )
                                else -> Brush.linearGradient(
                                    colors = listOf(
                                        Color.Red.copy(alpha = 0.6f),
                                        Color.Magenta.copy(alpha = 0.6f)
                                    )
                                )
                            }
                        }
                        ContactCard(contact = contact, gradient = gradient)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )
}

@Composable
fun ContactCard(contact: Contact, gradient: Brush) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(gradient)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = contact.email,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}


data class Contact(
    val name: String,
    val email: String
)







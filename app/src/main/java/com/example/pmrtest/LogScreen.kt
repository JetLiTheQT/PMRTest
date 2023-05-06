package com.example.pmrtest

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun LogScreen(navController: NavHostController) {
    val messages = remember { mutableStateListOf<String>() }
    val database = FirebaseDatabase.getInstance()
    val messagesRef = database.getReference("messages")

    // Listen for messages in the Firebase Realtime Database
    messagesRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            messages.clear()
            for (child in snapshot.children) {
                val message = child.child("message").getValue(String::class.java) ?: "Default Message"
                messages.add(message)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle the error
        }
    })
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        content = {innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //                    RetrieveToken() // COMMENTED OUT SINCE I ALREADY HAVE MY TOKEN. IF YOU NEED YOUR TOKEN CALL THIS FUNCTION AND CHECK THE LOG
                    AllReceivedMessages(messages) //For a log of all the times the patient has fallen
                }
            }
        }
    )

}

@Composable
fun AllReceivedMessages(messages: SnapshotStateList<String>) {
    var animateNewCard by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            itemsIndexed(messages.asReversed()) { index, message ->
                val expandContent = remember { mutableStateOf(false) }
                val cardHeight by animateDpAsState(
                    targetValue = if (expandContent.value) 200.dp else 100.dp,
                    animationSpec = tween(durationMillis = 300)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .clickable { expandContent.value = !expandContent.value },
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.body1,
                            maxLines = if (expandContent.value) Int.MAX_VALUE else 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (expandContent.value) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Index: $index",
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(messages.size) {
        if (messages.size > 1) {
            animateNewCard = true
        }
    }
}




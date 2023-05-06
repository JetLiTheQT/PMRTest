package com.example.pmrtest

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pmrtest.ui.theme.PMRTestTheme
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {

    private val messages = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Listen for messages in the Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val messagesRef = database.getReference("messages")

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

        setContent {
            PMRTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    RetrieveToken() // COMMENTED OUT SINCE I ALREADY HAVE MY TOKEN. IF YOU NEED YOUR TOKEN CALL THIS FUNCTION AND CHECK THE LOG
                    AllReceivedMessages(messages) //For a log of all the times the patient has fallen
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllReceivedMessages(messages: SnapshotStateList<String>) {
    var animateNewCard by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Received Messages",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
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
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = if (expandContent.value) Int.MAX_VALUE else 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (expandContent.value) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Index: $index",
                                style = MaterialTheme.typography.bodyMedium,
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




@Composable
fun RetrieveToken() {
    val context = LocalContext.current
    val token = rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        token.value = sharedPreferences.getString("fcm_token", null)
    }

    Column {
        if (token.value != null) {
            Text("FCM Token: ${token.value}")
            Log.d("RetrieveToken", "FCM Token: ${token.value}") //Find this in the log output and use this for your python script
        } else {
            Text("FCM Token not available")
        }
    }
}

//Python Script
/*
import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging
from datetime import datetime

cred = credentials.Certificate("PATH TO THE CREDENTIALS JSON. EX. MINE: pmr-test-cd8d2-firebase-adminsdk-fo4jd-05259879c0.json")
firebase_admin.initialize_app(cred)

def send_push_notification(token, message):
    timestamp = datetime.now().strftime("%Y-%m-%d at %I:%M%p")
    data = {
        'message': message,
    }

    message = messaging.Message(
        data=data,
        notification=messaging.Notification(
            title='Fall Alert',
            body=f"Patient has fallen at {timestamp}"
        ),
        token=token
    )

    response = messaging.send(message)
    print(f'Successfully sent message: {response}')

device_token = "INSERT DEVICE TOKEN AFTER RUNNING THE RETRIEVE_TOKEN() FUNCTION"
message = "Patient has fallen"

send_push_notification(device_token, message)

 */
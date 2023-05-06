package com.example.pmrtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.example.pmrtest.ui.theme.AppTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AppTheme {
                MyApp{
                    MainScreen()
                }

            }
        }
    }
}
@Composable
fun MyApp(content: @Composable () -> Unit) {
    Surface {
        content()
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
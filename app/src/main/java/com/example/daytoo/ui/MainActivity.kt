package com.example.daytoo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.daytoo.ui.components.AskOut
import com.example.daytoo.ui.components.Decision
import com.example.daytoo.ui.components.Greeting
import com.example.daytoo.ui.theme.DayTooTheme
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.daytoo.api.RetrofitInstance
import com.example.daytoo.models.NotificationData
import com.example.daytoo.ui.GalleryActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic"
const val TOPIC2 = "/topics/herTopic"
var notificationSent = true

class MainActivity : ComponentActivity() {

    private var id = ""
    private var title = "Hii Ananya"
    private var msg = "Usne App Khola"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var greet by remember { mutableStateOf(true) }
            var askOut by remember { mutableStateOf(false) }
            var decision by remember { mutableStateOf(false) }
            var decisionType by remember {
                mutableStateOf(false)
            }

            DayTooTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val pref = applicationContext.getSharedPreferences(
                        "MyPref",
                        0
                    )
                    val openApp = pref.getBoolean("Open_App", true)
                    if (!openApp) finish()
                    if (greet) Greeting("Ananya", showAskOut = {
                        greet = false
                        askOut = true
                        decision = false
                    },
                        btnClick = {
                            val intent = Intent(this, GalleryActivity::class.java)
                            startActivity(intent)
                        }
                    )
                    if (askOut) AskOut(showDecision = {
                        decision = true
                        greet = false
                        askOut = false
                        decisionType = it
                    })
                    if (decision) Decision(type = decisionType)
                }
            }
        }

        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        if (id != "5557b08b6338cd58") {
            title = "Hii Sudhanshu"
            msg = "Usne App Khola"
            FirebaseMessaging.getInstance().subscribeToTopic(TOPIC2)
        } else {
            FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        }
        askNotificationPermission()

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (notificationSent) {
                PushNotification(
                    NotificationData(title, msg),
                    if (id != "5557b08b6338cd58") TOPIC else TOPIC2
                ).also {
                    sendNotification(it)
                }
                notificationSent = false
            }
        } else {
            // TODO: Inform user that that your app will not show notifications.
            Toast.makeText(
                this,
                "Allow karde notification permission gadhi",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                if (notificationSent) {
                    PushNotification(
                        NotificationData(title, msg),
                        if (id != "5557b08b6338cd58") TOPIC else TOPIC2
                    ).also {
                        sendNotification(it)
                    }
                    notificationSent = false
                }
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            if (notificationSent) {
                PushNotification(
                    NotificationData(title, msg),
                    if (id != "5557b08b6338cd58") TOPIC else TOPIC2
                ).also {
                    sendNotification(it)
                }
                notificationSent = false
            }

        }
    }

    private fun sendNotification(notification: PushNotification) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Response: ${Gson().toJson(response)}")
                } else {
                    Log.e("MainActivity", response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e("MainActivity", e.toString())
            }
        }
    }
}
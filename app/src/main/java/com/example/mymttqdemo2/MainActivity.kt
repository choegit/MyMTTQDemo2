package com.example.mymttqdemo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable full edge-to-edge layout
        enableEdgeToEdge()

        // Initialize Firebase Analytics
        firebaseAnalytics = Firebase.analytics

        // Log an example custom event
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "MqttScreen")
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        // Load Compose UI
        setContent {
            MqttScreen()
        }
    }
}

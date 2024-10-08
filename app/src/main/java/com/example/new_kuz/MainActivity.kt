package com.example.new_kuz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.new_kuz.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            val client = Client(context)
//                .setEndpoint("https://cloud.appwrite.io/v1")
//                .setProject("66fd1bff000c6d5f9c7f")
//                .setSelfSigned(status = true)
            KUZTheme {
                MainNavigation()
            }
        }
    }
}
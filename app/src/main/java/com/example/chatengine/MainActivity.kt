package com.example.chatengine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatengine.HomeScreen.HomeScreen
import com.example.chatengine.Navigation.MyNav

import com.example.chatengine.ui.theme.ChatEngineTheme


class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatEngineTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
                    val username = remember {
                        mutableStateOf("")
                    }
                    val pwd = remember {
                        mutableStateOf("")
                    }
                    username.value = sharedPreferences.getString("USERNAME", "").toString()
                    pwd.value = sharedPreferences.getString("SECRET", "").toString()

                   MyNav(sharedPreferences)

                    //WebSocketScreen()

                }
            }
        }
    }
}


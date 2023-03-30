package com.example.chatengine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.chatengine.HomeScreen.HomeScreen
import com.example.chatengine.Navigation.MyNav
import com.example.chatengine.QuestionRoom.QuestionListing
import com.example.chatengine.QuestionRoom.QuestionViewModel
import com.example.chatengine.QuestionRoom.SubQuestion

import com.example.chatengine.ui.theme.ChatEngineTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences: SharedPreferences
    val questionViewModel by viewModels<QuestionViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                QuestionListing(questionViewModel)
            }
        }
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
//                    SubQuestion()

                    //WebSocketScreen()

                }
            }
        }
    }
}


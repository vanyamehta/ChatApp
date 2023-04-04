package com.example.chatengine.HomeScreen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import com.example.chatengine.R
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatengine.MainViewModel.MainViewModel
import com.example.chatengine.Login.LoginFunction

@Composable
fun Login(
    onNavigateToHome: () -> Unit,
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences,
    onsignUpClick: () -> Unit
) {




    val email = sharedPreferences.getString("USERNAME", "").toString()
    val secrett = sharedPreferences.getString("SECRET", "").toString()

    //println("*** $email")

    if (email.isNotBlank()){
        mainViewModel.user_name.value = email
        mainViewModel.password.value = secrett
//        HomeScreen(onChatClick = { }, mainViewModel =mainViewModel , sharedPreferences =  sharedPreferences, onFloatButttonclick = { })
        onNavigateToHome()
    }
    else {
        LoginScreen(onNavigateToHome,mainViewModel,sharedPreferences,onsignUpClick)

}}

@Composable
fun LoginScreen( onNavigateToHome: () -> Unit,mainViewModel:MainViewModel,sharedPreferences: SharedPreferences,onsignUpClick: () -> Unit) {
    val ctx: Context = LocalContext.current

    var userName = mainViewModel.user_name

    var password = mainViewModel.password
    var secret = remember {
        mutableStateOf("")
    }
    var result = remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.th),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxSize()
                .blur(6.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .alpha(0.9f)
                .clip(
                    CutCornerShape(
                        topEnd = 16.dp,
                        topStart = 8.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 16.dp
                    )
                )
                .background(Color.White)
        )
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(48.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
//
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.login), contentDescription = "login")
            Text(text = "WELCOME BACK", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = "Login to Continue", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        OutlinedTextField(
            modifier = Modifier
                .testTag("Enter your Username")
                .padding(16.dp)
                .fillMaxWidth(),
            value = userName.value,
            onValueChange = { userName.value = it },
            placeholder = { Text(text = "Enter your Username") },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {
                Icon(Icons.Default.Face, contentDescription = "Enter your Username")
            },
            singleLine = true,
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .testTag("Password"),
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = { Text(text = "Password") },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password")
            },
            singleLine = true,
        )
        mainViewModel.user_name=userName
        mainViewModel.password=password
        TextButton(onClick = {}, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Forgot Password")

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.navigationBarsPadding()) {
            Button(onClick = {
                LoginFunction(ctx,userName.value,password.value, result,secret, { onNavigateToHome() },mainViewModel,sharedPreferences)
            }, modifier = Modifier.fillMaxWidth().testTag("Login_Button")) {
                Text(text = "Login")
            }

            TextButton(onClick = { onsignUpClick() }) {
                Text(text = "Sign Up")
            }
        }

    }
}

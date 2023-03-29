package com.example.chatengine.HomeScreen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.example.chatengine.R
import androidx.compose.foundation.shape.CutCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatengine.Login.LoginViewModel
import com.example.chatengine.Login.getUserData

@Composable
fun Login(
    onNavigateToHome: () -> Unit,
    loginViewModel: LoginViewModel,
    sharedPreferences: SharedPreferences,
    onsignUpClick: () -> Unit
) {
    var userName = loginViewModel.user_name

    var password = loginViewModel.password
    var secret = remember {
        mutableStateOf("")
    }
    var result = remember {
        mutableStateOf("")
    }

    val ctx: Context = LocalContext.current

    val email = sharedPreferences.getString("USERNAME", "").toString()
    val secrett = sharedPreferences.getString("SECRET", "").toString()

    println("*** $email")

    if (email.isNotBlank()){
        loginViewModel.user_name.value = email
        loginViewModel.password.value = secrett
        getUserData(ctx,email,secrett,result,secret,onNavigateToHome,loginViewModel,sharedPreferences)
    }
    else {
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
            .padding(48.dp),
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
            value = userName.value,
            onValueChange = { userName.value = it },
            placeholder = { Text(text = "Username") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {
                Icon(Icons.Default.Face, contentDescription = "Username")
            },
            singleLine = true,
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = { Text(text = "password") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "password")
            },
            singleLine = true,
        )
        loginViewModel.user_name=userName
        loginViewModel.password=password
        TextButton(onClick = {}, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Forgot Password")

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                getUserData(ctx,userName.value,password.value, result,secret, { onNavigateToHome() },loginViewModel,sharedPreferences)
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Login")
            }

            TextButton(onClick = { onsignUpClick() }) {
                Text(text = "Sign Up")
            }
        }

    }
}}
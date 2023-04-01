package com.example.chatengine.HomeScreen


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatengine.SignUp.SignUpFunction
import com.example.chatengine.R

@Composable
fun SignUp(

    onNavigateToLogin:()->Unit
) {


    Box(modifier = Modifier.fillMaxSize()){
    Image(
        painter = painterResource(id= R.drawable.th),
        contentDescription = "signup",
        modifier = Modifier
            .fillMaxSize()
            .blur(6.dp),
            contentScale = ContentScale.Crop
    )

        Box(modifier = Modifier
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
        Column(
            Modifier
                .fillMaxSize()
                .padding(48.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround

        ){

            Image(painter = painterResource(id = R.drawable.signup), contentDescription = "Signup", modifier = Modifier
                .fillMaxWidth()

                .height(78.dp)
            )

            SignUpHeader({onNavigateToLogin()})
        }
    }
}

@Composable
fun SignUpHeader(

    onNavigateToLogin:()->Unit
) {
    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }
    val firstName = remember {
        mutableStateOf(TextFieldValue())
    }
    val lastName = remember {
        mutableStateOf(TextFieldValue())
    }
    val password = remember{
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            placeholder = { Text(text = "Username") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {
                Icon(Icons.Default.Face, contentDescription ="Username" )
            },
            singleLine = true,
        )

        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            placeholder = { Text(text = "First Name") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {Icon(Icons.Default.Person, contentDescription ="firstName" )},
            singleLine = true,
        )
        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            placeholder = { Text(text = "Last Name") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {Icon(Icons.Default.Person, contentDescription ="lastName" )},
            singleLine = true,

        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = { Text(text = "Password") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {Icon(Icons.Default.Lock, contentDescription ="password" )},
            singleLine = true,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    SignUpFunction(
                        ctx,userName,firstName,lastName,password,response
                    )
                    Toast.makeText(ctx,"SignUp Successful",Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Sign Up")
            }

            TextButton(onClick = { onNavigateToLogin() }) {
                Text(text = "Login")

            }

        }

    }

    }

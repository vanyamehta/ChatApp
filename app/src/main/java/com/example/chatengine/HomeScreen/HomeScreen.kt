package com.example.chatengine.HomeScreen


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import com.example.chatengine.MainViewModel.MainViewModel
import com.example.chatengine.MainActivity
import com.example.chatengine.MainChat.getMsgFunction
import com.example.chatengine.QuestionRoom.QuestionList
import com.example.chatengine.ui.theme.Purple700


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(
    onChatClick: () -> Unit,
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences,
    onFloatButttonclick: () -> Unit
) {
    var ctx : Context = LocalContext.current

    getAllRooms(ctx,mainViewModel)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

        getAllRooms(ctx, mainViewModel)
        editor.putString("USERNAME", mainViewModel.user_name.value)
        editor.putString("SECRET", mainViewModel.password.value)
        editor.apply()


    var result = remember {
        mutableStateOf("")
    }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Chats") },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        editor.putString("USERNAME", "")
                        editor.putString("SECRET", "")
                        editor.apply()
                        val intent = Intent(ctx, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        ctx.startActivity(intent)
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "LogOut")
                    }
                }, modifier = Modifier.statusBarsPadding()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFloatButttonclick()},
                content = { Icon(Icons.Filled.Add, "") }, modifier = Modifier.navigationBarsPadding()
            )

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
        ) {
            Column {

//                Text(
//                    text ="Welcome to Chat Engine",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Purple200,
//                )
//                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(1.0f)
                        .height(600.dp),

                    ) {
                    itemsIndexed(mainViewModel.allmsgRooms) { index, item ->
                        val time= item.created.substring(10,16)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(bottom = 5.dp)
                                //.background(Color.Black)
                                .clickable(onClick = {
                                    mainViewModel.chatid.value = item.id
                                    mainViewModel.accesskey.value = item.access_key
                                    onChatClick()
                                    mainViewModel.isLoading.value = true
                                    getMsgFunction(ctx, mainViewModel)
                                }),
                            shape = RoundedCornerShape(10.dp),
                            elevation = 10.dp
                        ) {
                            Row(
                                modifier = Modifier,
                                    //.background(cardBg),
                                //horizontalAlignment = Alignment.Start
                            ) {
                                Icon(
                                    Icons.Default.SupervisedUserCircle,
                                    contentDescription ="user",
                                    modifier = Modifier.size(80.dp),
                                    tint = Purple700
                                )
                                
                                Text(
                                    text = item.title,textAlign = TextAlign.Center,
                                    color= Color.Black,
                                    modifier = Modifier.padding(7.dp)
                                )
//                                IconButton(onClick = {
//                                    //AddMember(ctx,)
//
//                                }) {
//                                    Icon(
//                                        Icons.Filled.ArrowForward, contentDescription ="useradd",
//                                        modifier = Modifier.fillMaxWidth()
//                                            .align(Alignment.End)
//                                            .size(40.dp)
//                                    )
//                                }
                            }
                        }
                    }
                }
            }
            }
        }
}
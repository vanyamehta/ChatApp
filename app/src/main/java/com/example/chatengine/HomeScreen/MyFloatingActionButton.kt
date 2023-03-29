package com.example.chatengine.HomeScreen

import android.content.Context
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.chatengine.Login.LoginViewModel

@Composable
fun MyFloatingActionButton(loginViewModel: LoginViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var result = remember {
        mutableStateOf("")
    }
    var ctx:Context= LocalContext.current

    FloatingActionButton(
        onClick = { showDialog = true },
        content = { Icon(Icons.Filled.Add, "") }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Create Chat") },
            text = {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("title") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Do something with the text
                        postChat(ctx,text, result,loginViewModel)
                        showDialog = false
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

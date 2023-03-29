package com.example.chatengine.HomeScreen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatengine.MainViewModel.MainViewModel
import com.example.chatengine.R
import com.example.chatengine.SendMsgs.SendMsgFunction
import com.example.chatengine.WebSocket.WebSocketManager
import com.example.chatengine.typing.typingDataclass
import com.example.chatengine.ui.theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatScreen(mainViewModel: MainViewModel, webSocketManager: WebSocketManager, onClickGoBack:()->Unit) {

    var chat by remember {
        mutableStateOf("")
    }

    var result = remember {
        mutableStateOf("")
    }
    var ctx:Context= LocalContext.current

    val messageListState = mainViewModel.messageList.collectAsState()
    val messageList = messageListState.value
    
    Text(text = messageList.size.toString())


    Scaffold(topBar = {
        TopAppBar(
            title = {
                if(mainViewModel.istyping.value&&mainViewModel.user_name.value!=mainViewModel.istypinguser.value){
                    Text(text = " is typing")
                    mainViewModel.starttyping()
//                    isTYping=false
                }else{
                Text("user1") }
                },

            navigationIcon = {
                IconButton(onClick = { onClickGoBack()}) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.chatbg),
                contentDescription = "Background Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                reverseLayout = true
            ){
                itemsIndexed(mainViewModel.msghis.sortedByDescending { it.created }){ index, item ->
                    var user = item.sender_username==mainViewModel.user_name.value
                    val msgBg= if(user) UserChat else AgentChat
                    val textBg = if(user) Color.Black else Color.Black
                    val time= item.created.substring(10,16)

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = if(user) Alignment.End else Alignment.Start
                    ){
                        Box( modifier = Modifier.padding( bottom = 5.dp)){
                            Card(modifier = Modifier, shape = RoundedCornerShape(10.dp)) {
                                Column(
                                    modifier = Modifier
                                        .background(msgBg)
                                        .padding(bottom = 5.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text =item.text,
                                        color= textBg,
                                        modifier = Modifier.padding(7.dp)
                                    )

                                    Text(
                                        text = "$time",
                                        color= textBg,
                                        style= TextStyle(fontSize = 10.sp ),
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
        }


        Spacer(modifier = Modifier.height(50.dp))

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 8.dp), verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.08f)
                    ,
                    value = chat ,
                    onValueChange ={
                        chat=it
                        IsTypingHelpingFunction(ctx,mainViewModel)
                                   },
                    shape = RoundedCornerShape(10.dp)
                )

                IconButton(onClick = {
                    webSocketManager.sendMessage(chat)
                    SendMsgFunction(ctx,chat,result,mainViewModel)
                    chat=""
                }) {
                    Icon(Icons.Default.Send, contentDescription ="send Button", modifier = Modifier
                        .fillMaxWidth()
                        .size(40.dp),
                        Purple200 )
                }
            }

        }
    if(mainViewModel.isLoading.value==true){
        LoadingView()
    }
    }


@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}

fun IsTypingHelpingFunction(
    context: Context,
    mainViewModel: MainViewModel
)
{
    val retrofitAPI= mainViewModel.IsUserTyping()
    val call: Call<typingDataclass> = retrofitAPI.typing()
    call!!.enqueue(object : Callback<typingDataclass?> {
        override fun onResponse(call: Call<typingDataclass?>, response: Response<typingDataclass?>) {
            val model: typingDataclass? = response.body()
            val resp =
                "Response Code : " + response.code()
//            loginViewModel.result=resp
        }
        override fun onFailure(call: Call<typingDataclass?>, t: Throwable) {
            var temp = "Error found is : " + t.message
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()
        }
    })
}

    
    




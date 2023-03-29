package com.example.chatengine.WebSocket

import android.util.Log
import com.example.chatengine.MainViewModel.MainViewModel
import com.example.chatengine.GetMsgs.getMsgDataClass
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.net.SocketException

class WebSocketManager(private val mainViewModel: MainViewModel):WebSocketListener(){
    private var webSocket: WebSocket

    init {
        //val request = Request.Builder().url("wss://api.chatengine.io/chat/?projectID=bcf0bb7d-c035-4a42-a5c9-4a3d0dba0416&chatID=${loginViewModel.chatid}&accessKey=${loginViewModel.accesskey}").build()

        val request = Request.Builder().url("wss://api.chatengine.io/chat/?projectID=bcf0bb7d-c035-4a42-a5c9-4a3d0dba0416&chatID=153483&accessKey=ca-e64ff7cf-f914-4c9a-a3bf-ef2bf22d7e13").build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("MYTAG", "WebSocket connection established.")
        //webSocket.send("Hello, server!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val gson = Gson()
        val json = JSONObject(text)
        val action = json.getString("action")

        if (action == "new_message") {
            val message = json.getJSONObject("data").getJSONObject("message")
            val receivedMessage = getMsgDataClass(
                text = message.getString("text"),
                created = message.getString("created"),
                sender_username = message.getString("sender_username")
            )

            mainViewModel.updateMessageList((mainViewModel.messageList.value + message) as List<getMsgDataClass>)
            mainViewModel.webList(receivedMessage)
            Log.d("MYTAG", "onMessage: $receivedMessage ")
        }
        if (action =="is_typing") {
            val data=json.getJSONObject("data")
            val name=data.getString("person")
            mainViewModel.istyping.value=true
            mainViewModel.istypinguser.value= name.toString()
        }
    }

    fun onClose(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("MYTAG", "WebSocket connection closed.")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        if (t is SocketException && t.message?.contains("Broken pipe") == true) {
            Log.d("MYTAG", "WebSocket failure: Broken pipe")
            // Reconnect the WebSocket here
            val request = Request.Builder()

                .url("wss://api.chatengine.io/chat/?projectID=bcf0bb7d-c035-4a42-a5c9-4a3d0dba0416&chatID=153483&accessKey=ca-e64ff7cf-f914-4c9a-a3bf-ef2bf22d7e13")
                //.url("wss://api.chatengine.io/chat/?projectID=bcf0bb7d-c035-4a42-a5c9-4a3d0dba0416&chatID=${loginViewModel.chatid}&accessKey=${loginViewModel.accesskey}")
                .build()
            val client = OkHttpClient()
            this.webSocket = client.newWebSocket(request, this)
        }
        else{Log.d("MYTAG", "WebSocket failure.", t)}

    }

    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    fun closeWebSocket() {
        webSocket.close(1000, "Closing WebSocket.")
        Log.d("MYTAG","Connection Closed")
    }
}
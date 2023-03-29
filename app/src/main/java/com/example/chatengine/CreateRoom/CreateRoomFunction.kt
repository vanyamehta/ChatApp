package com.example.chatengine.CreateRoom

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.MainViewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun CreateRoomFunction(
    ctx: Context,
    title: String,
    result: MutableState<String>,
    mainViewModel: MainViewModel
) {
    val UserCreateAPI = mainViewModel.CreateNewChat()
    val Chat = CreateRoomDataClass(title.toString(), true)
    val call: Call<CreateRoomDataClass?>? = UserCreateAPI.createUser(Chat)
    call!!.enqueue(object : Callback<CreateRoomDataClass?> {
        override fun onResponse(call: Call<CreateRoomDataClass?>, response: Response<CreateRoomDataClass?>) {
            Toast.makeText(ctx, "ChatRoom Created", Toast.LENGTH_SHORT).show()
            val model: CreateRoomDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model?.title + "\n" + "Job : " + model?.is_direct_chat
            result.value = resp
            mainViewModel.newChatDetails = model

        }
        override fun onFailure(call: Call<CreateRoomDataClass?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })
}
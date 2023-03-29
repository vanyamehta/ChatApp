package com.example.chatengine.HomeScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.Login.LoginViewModel
import com.example.chatengine.MainChat.AllMsgDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun postChat(
    ctx:Context,
    title: String,
    result: MutableState<String>,
    //secret: MutableState<String>,
    //onChatClick:()->Unit,
    loginViewModel: LoginViewModel
) {

    val UserCreateAPI = loginViewModel.CreateNewChat()
    val Chat = ChatDataModel(title.toString(), true)
    val call: Call<ChatDataModel?>? = UserCreateAPI.createUser(Chat)
    call!!.enqueue(object : Callback<ChatDataModel?> {
        override fun onResponse(call: Call<ChatDataModel?>, response: Response<ChatDataModel?>) {
            Toast.makeText(ctx, "ChatRoom Created", Toast.LENGTH_SHORT).show()
            val model: ChatDataModel? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model?.title + "\n" + "Job : " + model?.is_direct_chat
            result.value = resp
            loginViewModel.newChatDetails = model
//            if(model?.is_direct_chat==true){
//                onChatClick()
//            }
        }
        override fun onFailure(call: Call<ChatDataModel?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })
}


fun getAllRooms(
    ctx:Context,

    loginViewModel: LoginViewModel
) {

    val RoomsAPI = loginViewModel.getALlRoomData()
    val call: Call<List<getChatHistory>?>? =RoomsAPI.history()
    call!!.enqueue(object : Callback<List<getChatHistory>?> {
        override fun onResponse(
            call: Call<List<getChatHistory>?>,
            response: Response<List<getChatHistory>?>
        ) {
            Toast.makeText(ctx, "Get all msg", Toast.LENGTH_SHORT).show()
            val model: List<getChatHistory>? = response.body()
            //val resp = model
//            if (resp != null) {
//                result.value = resp.toString()
//            }
            // if (model != null) {
            loginViewModel.allmsgRooms = model as MutableList<getChatHistory>
            //}
            // println("//////////////////////////////////////////////////////////////${ loginViewModel.msghis.size }")

        }

        override fun onFailure(call: Call<List<getChatHistory>?>, t: Throwable) {
            //result.value = "Error found is : " + t.message
        }
    })

}

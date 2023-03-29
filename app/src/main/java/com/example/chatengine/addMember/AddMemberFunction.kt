package com.example.chatengine.addMember

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.HomeScreen.ChatDataModel
import com.example.chatengine.Login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun AddMember(
    ctx: Context,
    username: String,
    result: MutableState<String>,
    //secret: MutableState<String>,
    //onChatClick:()->Unit,
    loginViewModel: LoginViewModel
) {

    val memberAPI = loginViewModel.memberData()
    val Chat = AddMemberDataClass(username.toString())
    val call: Call<AddMemberDataClass?>? = memberAPI.addMember(Chat)
    call!!.enqueue(object : Callback<AddMemberDataClass?> {
        override fun onResponse(call: Call<AddMemberDataClass?>, response: Response<AddMemberDataClass?>) {
            Toast.makeText(ctx, "ChatRoom Created", Toast.LENGTH_SHORT).show()
            val model: AddMemberDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model?.username
            result.value = resp
            loginViewModel.MemberData = model
//            if(model?.is_direct_chat==true){
//                onChatClick()
//            }
        }
        override fun onFailure(call: Call<AddMemberDataClass?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })
}
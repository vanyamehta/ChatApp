package com.example.chatengine.MainChat

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.Login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun SendChat(
    ctx:Context,
    text: String,
    result: MutableState<String>,
    loginViewModel: LoginViewModel
){

    val ChattingAPI= loginViewModel.StartChat()
    val Msg= ChattingDataModel(text.toString())
    val call: Call<ChattingDataModel?>? = ChattingAPI.SendMsg(Msg)
    call!!.enqueue(object : Callback<ChattingDataModel?> {
        override fun onResponse(call: Call<ChattingDataModel?>, response: Response<ChattingDataModel?>) {
            Toast.makeText(ctx, "Msg Send", Toast.LENGTH_SHORT).show()
            val model: ChattingDataModel? = response.body()
            val resp = model?.text
            if (resp != null) {
                result.value = resp
            }
            loginViewModel.textdata=model

        }

        override fun onFailure(call: Call<ChattingDataModel?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })

}

fun SeenChat(
    ctx:Context,
    result: MutableState<String>,
    loginViewModel: LoginViewModel
){

    val ChattingAPI=loginViewModel.msgHistory()
    val call: Call<List<AllMsgDataClass>?>? = ChattingAPI.getMsg()
    call!!.enqueue(object : Callback<List<AllMsgDataClass>?> {
        override fun onResponse(call: Call<List<AllMsgDataClass>?>, response: Response<List<AllMsgDataClass>?>) {
            Toast.makeText(ctx, "Get all msg", Toast.LENGTH_SHORT).show()
            loginViewModel.isLoading.value=false
            val model: List<AllMsgDataClass>? = response.body()
            //val resp = model
//            if (resp != null) {
//                result.value = resp.toString()
//            }
           // if (model != null) {
                loginViewModel.msghis= model as MutableList<AllMsgDataClass>
            //}
           // println("//////////////////////////////////////////////////////////////${ loginViewModel.msghis.size }")

        }

        override fun onFailure(call: Call<List<AllMsgDataClass>?>, t: Throwable) {
            //result.value = "Error found is : " + t.message
        }
    })

}
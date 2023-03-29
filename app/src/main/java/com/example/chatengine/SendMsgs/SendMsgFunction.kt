package com.example.chatengine.SendMsgs

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.SendMsgs.SendMsgDataClass
import com.example.chatengine.MainViewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun SendMsgFunction(
    ctx: Context,
    text: String,
    result: MutableState<String>,
    mainViewModel: MainViewModel
){

    val ChattingAPI= mainViewModel.StartChat()
    val Msg= SendMsgDataClass(text.toString())
    val call: Call<SendMsgDataClass?>? = ChattingAPI.SendMsg(Msg)
    call!!.enqueue(object : Callback<SendMsgDataClass?> {
        override fun onResponse(call: Call<SendMsgDataClass?>, response: Response<SendMsgDataClass?>) {
            Toast.makeText(ctx, "Msg Send", Toast.LENGTH_SHORT).show()
            val model: SendMsgDataClass? = response.body()
            val resp = model?.text
            if (resp != null) {
                result.value = resp
            }
            mainViewModel.textdata=model

        }

        override fun onFailure(call: Call<SendMsgDataClass?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })

}

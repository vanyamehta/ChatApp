package com.example.chatengine.MainChat

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.GetMsgs.getMsgDataClass
import com.example.chatengine.MainViewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun getMsgFunction(
    ctx:Context,
    mainViewModel: MainViewModel
){

    val ChattingAPI=mainViewModel.msgHistory()
    val call: Call<List<getMsgDataClass>?>? = ChattingAPI.getMsg()
    call!!.enqueue(object : Callback<List<getMsgDataClass>?> {
        override fun onResponse(call: Call<List<getMsgDataClass>?>, response: Response<List<getMsgDataClass>?>) {
            Toast.makeText(ctx, "Get all msg", Toast.LENGTH_SHORT).show()
            mainViewModel.isLoading.value=false
            val model: List<getMsgDataClass>? = response.body()
                mainViewModel.msghis= model as MutableList<getMsgDataClass>
        }

        override fun onFailure(call: Call<List<getMsgDataClass>?>, t: Throwable) {

        }
    })

}
package com.example.chatengine.Login

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.HomeScreen.getAllRooms
import com.example.chatengine.MainViewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun LoginFunction(
    ctx: Context,
    username:String,
    password:String,
    result: MutableState<String>,
    secret: MutableState<String>,
    onNavigateToHome: () -> Unit,
    viewModel: MainViewModel,
    sharedPreferences: SharedPreferences
) {
    val GetAPI= viewModel.AuthenticateUser()
    val call: Call<LoginDataClass?>? = GetAPI.getUser()
    call!!.enqueue(object : Callback<LoginDataClass?> {
        override fun onResponse(call: Call<LoginDataClass?>, response: Response<LoginDataClass?>) {
            Toast.makeText(ctx, "Login Successful", Toast.LENGTH_SHORT).show()
            val model: LoginDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model?.is_authenticated + "\n" + "Job : " + model?.username
            result.value = resp
            secret.value=model?.secret.toString()
            viewModel.UserData=model
            if(model?.is_authenticated==true){
                onNavigateToHome()
            }

        }

        override fun onFailure(call: Call<LoginDataClass?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })

}



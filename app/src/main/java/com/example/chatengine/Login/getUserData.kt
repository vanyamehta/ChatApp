package com.example.chatengine.Login

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.chatengine.HomeScreen.getAllRooms
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getUserData(
    ctx: Context,
    username:String,
    password:String,
    result: MutableState<String>,
    secret: MutableState<String>,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel,
    sharedPreferences: SharedPreferences
) {

    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val GetAPI= viewModel.AuthenticateUser()
    val call: Call<LoginDataModel?>? = GetAPI.getUser()
    call!!.enqueue(object : Callback<LoginDataModel?> {
        override fun onResponse(call: Call<LoginDataModel?>, response: Response<LoginDataModel?>) {
            Toast.makeText(ctx, "Login Successful", Toast.LENGTH_SHORT).show()
            val model: LoginDataModel? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model?.is_authenticated + "\n" + "Job : " + model?.username
            result.value = resp
            secret.value=model?.secret.toString()
            viewModel.UserData=model
            if(model?.is_authenticated==true){
                onNavigateToHome()
            }
            if (response.isSuccessful){
                getAllRooms(ctx, viewModel)
                editor.putString("USERNAME", username)
                editor.putString("SECRET", password)
                editor.apply()
            }
        }

        override fun onFailure(call: Call<LoginDataModel?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })

}



package com.example.chatengine.SignUp


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {

    @POST("users/")
    fun postData(@Body dataModel: DataModel?) : Call<DataModel?>?
}

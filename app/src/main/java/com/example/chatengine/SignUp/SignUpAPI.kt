package com.example.chatengine.SignUp


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpAPI {

    @POST("users/")
    fun postData(@Body signUpDataClass: SignUpDataClass?) : Call<SignUpDataClass?>?
}

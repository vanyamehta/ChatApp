package com.example.chatengine.Login

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface LoginAPI {

    @GET("me/")
    fun getUser() : Call<LoginDataClass?>?
}

class LoginClass(username:String,password:String){
    val username=username
    val password=password
    val url = "https://api.chatengine.io/users/"

    fun LoginInstance():LoginAPI {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    //.addHeader(headerKey, privateKey)
                    .addHeader("Project-ID", "7135f244-46a1-4b8d-a21a-2240f55119ca")
                    .addHeader("User-Name", username)
                    .addHeader("User-Secret", password)
                    .build()
                chain.proceed(request)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LoginAPI::class.java)
        return retrofit


    }
}
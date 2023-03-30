package com.example.chatengine.SendMsgs

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface SendMsgAPI {

    @POST("messages/")
    fun SendMsg(@Body dataModel: SendMsgDataClass?): Call<SendMsgDataClass?>?

}

class SendMsgClass(
    username: String,
    password: String,

    val chatid: Int,
    //val chatid: Int,
){
    val username= username
    val password= password
    fun sendMsgInstance(): SendMsgAPI {
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor{ chain->
                val originalRequest = chain.request().newBuilder()
                    .addHeader("Project-ID","bcf0bb7d-c035-4a42-a5c9-4a3d0dba0416")
                    .addHeader("User-Name",username)
                    .addHeader("User-Secret",password)
                    .build()
                chain.proceed(originalRequest)
            }
            .build()
        val chatAPI= Retrofit.Builder()
            .baseUrl("https://api.chatengine.io/chats/$chatid/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SendMsgAPI::class.java)
        return chatAPI
    }
}
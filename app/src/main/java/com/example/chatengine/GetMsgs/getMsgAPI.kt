package com.example.chatengine.MainChat


import com.example.chatengine.GetMsgs.getMsgDataClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface getMsgAPI {


    @GET("messages/")
    fun getMsg(): Call<List<getMsgDataClass>?>?

}

class getMsgClass(
    username:String,
    password: String,
    ){
    val username= username
    val password= password
    fun getMsgInstance(): getMsgAPI {
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
            .baseUrl("https://api.chatengine.io/chats/153483/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(getMsgAPI::class.java)
        return chatAPI
    }

}
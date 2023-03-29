package com.example.chatengine.MainChat


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChattingAPI {

    @POST("messages/")
    fun SendMsg(@Body dataModel: ChattingDataModel?):Call<ChattingDataModel?>?

    @GET("messages/")
    fun getMsg(): Call<List<AllMsgDataClass>?>?
}

class MainChattingClass(username:String,password:String,val chatid:String,val accesskey:String){
    val username= username
    val password= password
    fun postChatInstance(): ChattingAPI {
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
            .build().create(ChattingAPI::class.java)
        return chatAPI
    }
}

class MsgSending(username:String,password: String,val chatid:String){
    val username= username
    val password= password
    fun seeChatInstance(): ChattingAPI {
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
            .build().create(ChattingAPI::class.java)
        return chatAPI
    }

}
package com.example.chatengine.HomeScreen

import com.example.chatengine.GetRooms.getRoomDataClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface getRoomsAPI {
    
    @GET("chats/")
    fun history() :Call<List<getRoomDataClass>?>?

}

class getAllchatRooms(username:String,password:String){
    val username= username
    val password= password
    fun roomInstance():getRoomsAPI{
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor{ chain->
                val originalRequest = chain.request().newBuilder()
                    .addHeader("Project-ID","7135f244-46a1-4b8d-a21a-2240f55119ca")
                    .addHeader("User-Name",username)
                    .addHeader("User-Secret",password)
                    .build()
                chain.proceed(originalRequest)
            }
            .build()
        val chatAPI=Retrofit.Builder()
            .baseUrl("https://api.chatengine.io/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(getRoomsAPI::class.java)
        return chatAPI
    }
}
package com.example.chatengine.addMember


import com.example.chatengine.HomeScreen.UserCreateAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface NewMemberAPI {

    @POST("people/")
    fun addMember(@Body datamodel:AddMemberDataClass?) : Call<AddMemberDataClass?>?
}


class NewMemberClass(username:String,password:String,var membername:String){
    val username= username
    val password= password
    fun memberInstance(): NewMemberAPI {
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
            .baseUrl("https://api.chatengine.io/chats/{$membername}/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewMemberAPI::class.java)
        return chatAPI
    }
}
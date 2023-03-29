package com.example.chatengine.SignUp

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun postDataUsingAPI(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    firstName: MutableState<TextFieldValue>,
    lastName: MutableState<TextFieldValue>,
    password: MutableState<TextFieldValue>,
    result: MutableState<String>
) {

    var url = "https://api.chatengine.io/"
    val privateKey = "9850c9e8-443a-46b4-bb69-42806efce470"
    val headerKey = "PRIVATE-KEY"

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(headerKey, privateKey)
                .build()
            chain.proceed(request)
        }
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
    val dataModel = DataModel(userName.value.text, firstName.value.text,lastName.value.text,password.value.text)
    val call: Call<DataModel?>? = retrofitAPI.postData(dataModel)
    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {
            Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()
            val model: DataModel? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model!!.username + "\n" + "Job : " + model!!.first_name
            result.value = resp
        }

        override fun onFailure(call: Call<DataModel?>?, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })
    
}
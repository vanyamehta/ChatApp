package com.example.chatengine.HomeScreen

import android.content.Context
import android.widget.Toast
import com.example.chatengine.GetRooms.getRoomDataClass
import com.example.chatengine.MainViewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getAllRooms(
    ctx:Context,
    mainViewModel: MainViewModel
) {

    val RoomsAPI = mainViewModel.getALlRoomData()
    val call: Call<List<getRoomDataClass>?>? =RoomsAPI.history()
    call!!.enqueue(object : Callback<List<getRoomDataClass>?> {
        override fun onResponse(
            call: Call<List<getRoomDataClass>?>,
            response: Response<List<getRoomDataClass>?>
        ) {
            Toast.makeText(ctx, "Rooms fetched", Toast.LENGTH_SHORT).show()
            val model: List<getRoomDataClass>? = response.body()

            mainViewModel.allmsgRooms = model as MutableList<getRoomDataClass>


        }

        override fun onFailure(call: Call<List<getRoomDataClass>?>, t: Throwable) {

        }
    })

}

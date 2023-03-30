package com.example.chatengine.CreateRoom

data class CreateRoomDataClass(
    var title: String,
    val is_direct_chat: Boolean,
    val usernames:List<String>
)

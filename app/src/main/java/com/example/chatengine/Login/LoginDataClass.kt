package com.example.chatengine.Login

data class LoginDataClass(
    val avatar:Any,
    val username: String,
    val first_name: String,
    val last_name: String,
    val is_online: Boolean,
    val secret: String,
    val is_authenticated:Boolean
)

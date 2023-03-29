package com.example.chatengine.MainViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatengine.CreateRoom.CreateRoomAPI
import com.example.chatengine.CreateRoom.CreateRoomClass
import com.example.chatengine.CreateRoom.CreateRoomDataClass
import com.example.chatengine.GetMsgs.getMsgDataClass
import com.example.chatengine.GetRooms.getRoomDataClass
import com.example.chatengine.HomeScreen.*
import com.example.chatengine.Login.LoginAPI
import com.example.chatengine.Login.LoginClass
import com.example.chatengine.Login.LoginDataClass
import com.example.chatengine.MainChat.*
import com.example.chatengine.SendMsgs.SendMsgAPI
import com.example.chatengine.SendMsgs.SendMsgClass
import com.example.chatengine.SendMsgs.SendMsgDataClass
import com.example.chatengine.addMember.AddMemberDataClass
import com.example.chatengine.addMember.NewMemberAPI
import com.example.chatengine.addMember.NewMemberClass
import com.example.chatengine.typing.TypingAPI
import com.example.chatengine.typing.TypingClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class MainViewModel:ViewModel() {

    private val _messageList = MutableStateFlow(emptyList<getMsgDataClass>())
    val messageList: StateFlow<List<getMsgDataClass>> = _messageList

    fun updateMessageList(newList: List<getMsgDataClass>) {
        _messageList.value = newList
    }

//    login screen credentials
    var user_name = mutableStateOf("")
    var password  = mutableStateOf("")
    var initial_Data = LoginDataClass("","","","",false,"",false)
    var UserData: LoginDataClass? by mutableStateOf(initial_Data)
//   for checking credentials of user who is logging in
    fun AuthenticateUser(): LoginAPI {
        val apiService = LoginClass(user_name.value,password.value).LoginInstance()
        return apiService
    }

//   for creating a new room
    var chat_name by mutableStateOf("")
    var tempchat= CreateRoomDataClass(chat_name,false)
    var newChatDetails : CreateRoomDataClass? by mutableStateOf(tempchat)
    fun CreateNewChat(): CreateRoomAPI {
        val chatapiSercice = CreateRoomClass(user_name.value,password.value).CreateRoomInstance()
        return chatapiSercice
    }


//  for getting all the rooms
    var allmsgRooms :MutableList<getRoomDataClass> by mutableStateOf(mutableListOf())
    fun getALlRoomData():getRoomsAPI{
        val roomapi= getAllchatRooms(user_name.value,password.value).roomInstance()
        return roomapi
    }

//     var chatid = mutableStateOf(-1)
//     var accesskey= mutableStateOf("")


//  for sending Msgs
    var text by mutableStateOf("")
    var firsttext = SendMsgDataClass("")
    var textdata: SendMsgDataClass? by mutableStateOf(firsttext)
//
//    fun StartChat():SendMsgAPI{
//        val msgApiService =SendMsgClass(user_name.value,password.value,chatid.value).sendMsgInstance()
//        return msgApiService
//    }

    fun StartChat():SendMsgAPI{
        val msgApiService =SendMsgClass(user_name.value,password.value).sendMsgInstance()
        return msgApiService
    }



//   for getting all the msgs
    var msghis: MutableList<getMsgDataClass> by mutableStateOf(mutableListOf())

//    fun msgHistory():ChattingAPI{
//        val chatapi = MsgSending(user_name.value,password.value,chatid).seeChatInstance()
//        return chatapi
//    }

    fun msgHistory():getMsgAPI{
        val chatapi = getMsgClass(user_name.value,password.value).getMsgInstance()
        return chatapi
    }


// updating Msgs from websocket
    fun webList(msg: getMsgDataClass){
        msghis.add(msg)
    }

//  for loader indication
    var isLoading = mutableStateOf(false)


//  for creating members
    var NewMemberName by mutableStateOf("")
    var newMember= AddMemberDataClass(NewMemberName)
    var MemberData : AddMemberDataClass? by mutableStateOf(newMember)
    fun memberData(): NewMemberAPI {
        val memberapiservice = NewMemberClass (user_name.value,password.value,NewMemberName).memberInstance()
        return memberapiservice
    }

// for checking user is typing
    val istyping = mutableStateOf(false)
    val istypinguser= mutableStateOf("")


//    fun IsUserTyping(): TypingAPI {
//        val apiService= TypingClass(user_name.value,password.value,chatid).getTypingInstance()
//        return apiService
//    }

    fun IsUserTyping(): TypingAPI {
        val apiService= TypingClass(user_name.value,password.value,153483).getTypingInstance()
        return apiService
    }
    fun starttyping(){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                delay(2000L)
            }
            istyping.value=false
        }
    }


}
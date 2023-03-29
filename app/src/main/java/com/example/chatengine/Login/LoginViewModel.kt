package com.example.chatengine.Login

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatengine.HomeScreen.*
import com.example.chatengine.MainChat.*
import com.example.chatengine.addMember.AddMemberDataClass
import com.example.chatengine.addMember.NewMemberAPI
import com.example.chatengine.addMember.NewMemberClass
import com.example.chatengine.typingStatus.TypingAPI
import com.example.chatengine.typingStatus.TypingClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class LoginViewModel:ViewModel() {

    private val _messageList = MutableStateFlow(emptyList<AllMsgDataClass>())
    val messageList: StateFlow<List<AllMsgDataClass>> = _messageList

    fun updateMessageList(newList: List<AllMsgDataClass>) {
        _messageList.value = newList
    }



    var user_name = mutableStateOf("")
    var password  = mutableStateOf("")
    var initial_Data =LoginDataModel("","","","",false,"",false)
    var UserData: LoginDataModel? by mutableStateOf(initial_Data)

    fun AuthenticateUser():GetAPI{
        val apiService = LoginClass(user_name.value,password.value).getInstance()
        return apiService
    }


    var chat_name by mutableStateOf("")
    var tempchat= ChatDataModel(chat_name,false)
    var newChatDetails : ChatDataModel? by mutableStateOf(tempchat)


    fun CreateNewChat(): UserCreateAPI {
        val chatapiSercice = ChatClass(user_name.value,password.value).postInstance()
        return chatapiSercice
    }

    var allmsgRooms :MutableList<getChatHistory> by mutableStateOf(mutableListOf())
    fun getALlRoomData():UserCreateAPI{
        val roomapi= getAllchatRooms(user_name.value,password.value).roomInstance()
        return roomapi
    }

//     var chatid by mutableStateOf(-1)
//     var accesskey= mutableStateOf("")


    var text by mutableStateOf("")
    var firsttext = ChattingDataModel("")
    var textdata: ChattingDataModel? by mutableStateOf(firsttext)
//
//    fun StartChat():ChattingAPI{
//        val msgApiService =MainChattingClass(user_name.value,password.value,chatid).postChatInstance()
//        return msgApiService
//    }

    fun StartChat():ChattingAPI{
        val msgApiService =MainChattingClass(user_name.value,password.value).postChatInstance()
        return msgApiService
    }



    var msghis: MutableList<AllMsgDataClass> by mutableStateOf(mutableListOf())

//    fun msgHistory():ChattingAPI{
//        val chatapi = MsgSending(user_name.value,password.value,chatid).seeChatInstance()
//        return chatapi
//    }

    fun msgHistory():ChattingAPI{
        val chatapi = MsgSending(user_name.value,password.value).seeChatInstance()
        return chatapi
    }

    fun webList(msg:AllMsgDataClass){
        msghis.add(msg)
    }

    var isLoading = mutableStateOf(false)



    var NewMemberName by mutableStateOf("")
    var newMember= AddMemberDataClass(NewMemberName)
    var MemberData : AddMemberDataClass? by mutableStateOf(newMember)


    fun memberData(): NewMemberAPI {
        val memberapiservice = NewMemberClass (user_name.value,password.value,NewMemberName).memberInstance()
        return memberapiservice
    }


    val istyping = mutableStateOf(false)
    val istypinguser= mutableStateOf("")

    @SuppressLint("SuspiciousIndentation")
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
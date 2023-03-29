package com.example.chatengine.Navigation


import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatengine.HomeScreen.ChatScreen
import com.example.chatengine.HomeScreen.HomeScreen
import com.example.chatengine.HomeScreen.Login
import com.example.chatengine.HomeScreen.SignUp
import com.example.chatengine.Login.LoginViewModel
import com.example.chatengine.WebSocket.WebSocketManager

@Composable
fun MyNav(sharedPreferences: SharedPreferences,navController: NavHostController= rememberNavController()) {
    val loginViewModel:LoginViewModel= viewModel()
    val webSocketManager= WebSocketManager(loginViewModel)

    NavHost(navController = navController, startDestination = "login_screen" ){
        composable(route="login_screen"){
            Login(
                onNavigateToHome = {
                    navController.navigate("home_screen")
                },loginViewModel,sharedPreferences
            ) {
                navController.navigate("sign_screen")
            }
        }
        composable(route = "home_screen"){
            HomeScreen(
                    onChatClick={
                navController.navigate("chatting_screen")
            }, loginViewModel,sharedPreferences
            )
        }
        composable(route="sign_screen"){
            SignUp(
                onNavigateToLogin = {
                    navController.navigate("login_screen")
                }

            )
        }
        composable(route="chatting_screen"){
            ChatScreen(
                loginViewModel,
                webSocketManager,
                onClickGoBack={
                    navController.navigate("home_screen")
                }
            )
        }
    }

    
}



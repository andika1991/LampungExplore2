package com.example.lampungexplore
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "daftar") {
        composable("daftar") {
            RegisterScreen(navController = navController, onRegisterSuccess = {
                navController.navigate("success_screen")
            })
        }
        composable("success_screen") {
            SuccessScreen()
        }
    }
}

@Composable
fun SuccessScreen() {
    // Define your success screen UI here
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registration Successful!")
        // Add more UI elements as needed
    }
}

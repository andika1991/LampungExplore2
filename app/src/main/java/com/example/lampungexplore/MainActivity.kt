package com.example.lampungexplore
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lampungexplore.ui.theme.LampungExploreTheme
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.app.Application
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val currentUser = FirebaseAuth.getInstance().currentUser
        setContent {
            LampungExploreTheme {
                val navController = rememberNavController()


                // Set graf navigasi ke NavHostController
                val startDestination = if (currentUser != null) "HomeScreen" else "loginScreen"

                AppContent(navController = navController, startDestination = startDestination ,currentUser =currentUser)
            }
        }
    }
}


@Composable
fun AppContent(navController: NavHostController, startDestination: String, currentUser: FirebaseUser?) {
    var showSplash by remember { mutableStateOf(true) }
    var showLogin by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000L) // 2 seconds delay
        showSplash = false
        showLogin = true
    }

    NavHost(navController, startDestination = startDestination) {
        composable("loginScreen") {
            if (showSplash) {
                SplashScreen()
            } else if (showLogin) {
                LoginScreen(navController = navController) {
                    navController.navigate("daftar") // Navigasi ke halaman pendaftaran
                }
            }
        }
        composable("daftar") {
            RegisterScreen(navController = navController) {

            }
        }
        composable(
            route = "HomeScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            HomeScreen(navController, currentUser)
        }

        composable(
            route = "DaftarWisataLampungUtaraScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataLampungUtaraScreen(navController = navController)

        }

        composable("DeskripsiWisataScreen/{nama}/{deskripsi}", enterTransition = null,
            exitTransition = null) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val deskripsi = backStackEntry.arguments?.getString("deskripsi")
            DeskripsiWisataScreen(nama = nama ?: "", deskripsi = deskripsi ?: "")
        }
    }
}



@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.lampungexplorere),
                contentDescription = "Splash Image"
            )
        }
    }
}

@Composable
fun LoginScreen(navController: NavController, onRegisterClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome message
            Text(
                text = "Selamat Datang di Aplikasi Lampung Explore",
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Lampung Explore Icon
            Image(
                painter = painterResource(id = R.drawable.lampungexplorere),
                contentDescription = "Lampung Explore Icon",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Password field
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.padding(horizontal = 16.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Login button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Jika login berhasil, arahkan ke halaman home APK
                                    navController.navigate("HomeScreen")
                                } else {
                                    errorMessage = "Email atau password salah"
                                }
                            }
                    } else {
                        errorMessage = "Email dan password harus diisi"
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("Login")
            }

            // Menampilkan pesan kesalahan
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            // Footer
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Belum punya akun? ")
                ClickableText(
                    text = AnnotatedString(" Klik daftar sekarang"),
                    onClick = { onRegisterClick() }
                )
            }
        }
    }
}

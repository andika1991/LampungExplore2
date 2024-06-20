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
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavType
import androidx.navigation.navArgument
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

    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)


        mediaPlayer = MediaPlayer.create(this, R.raw.lampungkita) // Ganti your_audio_file dengan nama file audio Anda
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        setContent {
            val currentUser = FirebaseAuth.getInstance().currentUser
            LampungExploreTheme {
                val navController = rememberNavController()
                AppContent(navController = navController)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Hentikan audio ketika aktivitas dihancurkan
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}


@Composable
fun AppContent(navController: NavHostController) {
    NavHost(navController, startDestination = "splashScreen") {
        composable("splashScreen") {
            SplashScreen(navController)
        }
        composable("loginScreen") {
            LoginScreen(navController = navController) {
            }


        }
        composable("RegisterScreen") {
            RegisterScreen(navController = navController) {
            }


        }
        composable(
            route = "HomeScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            HomeScreen(navController, currentUser)
        }
        composable(
            route = "DaftarWisataLampungUtaraScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataLampungUtaraScreen(navController = navController)
        }
        composable(
            route = "DeskripsiWisataScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisataScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreen/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreen/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreen(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataLampungSelatanScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataLampungSelatanScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatalamselScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatalamselScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenlamsel/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenlamsel/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenlamsel(googleMapsUrl = googleMapsUrl)
        }
        composable(
            route = "DaftarWisataLampungBaratScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataLampungBaratScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatalambarScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatalambarScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenlambar/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenlambar/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenlambar(googleMapsUrl = googleMapsUrl)
        }
        composable(
            route = "DaftarWisataLampungTengahScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataLampungTengahScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatalamtengScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatalamtengScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenlamteng/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenlamteng/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenlamteng(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataBandarLampungScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataBandarLampungScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatabalamScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatabalamScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenbalam/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenbalam/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenbalam(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataLampungTimurScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataLampungTimurScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatalamtimScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatalamtimScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenlamtim/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenlamtim/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenlamtim(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataMesujiScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataMesujiScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatamesujiScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatamesujiScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenmesuji/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenmesuji/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenmesuji(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataMetroScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataMetroScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatametroScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatametroScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenmetro/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenmetro/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenmetro(googleMapsUrl = googleMapsUrl)
        }
        composable(
            route = "DaftarWisataPesawaranScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataPesawaranScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatapesawaranScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatapesawaranScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenpesawaran/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenpesawaran/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenpesawaran(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataPesisirBaratScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataPesisirBaratScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatapesibarScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatapesibarScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenpesibar/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenpesibar/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenpesibar(googleMapsUrl = googleMapsUrl)
        }


        composable(
            route = "DaftarWisataPringsewuScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataPringsewuScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatapswScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatapswScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenpsw/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreenpsw/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreenpsw(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataTanggamusScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataTanggamusScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatatgmsScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatatgmsScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreentgms/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreentgms/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreentgms(googleMapsUrl = googleMapsUrl)
        }
        composable(
            route = "DaftarWisataTubabaScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataTubabaScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatatubabaScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatatubabaScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreentubaba/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreentubaba/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreentubaba(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataTulangBawangScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataTulangBawangScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatawayScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatawayScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreentuba/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "MapsScreentuba/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            // Display the map screen using the provided URL
            MapsScreentuba(googleMapsUrl = googleMapsUrl)
        }

        composable(
            route = "DaftarWisataWayKananScreen",
            enterTransition = null,
            exitTransition = null
        ) {
            DaftarWisataWayKananScreen(navController = navController)
        }

        composable(
            route = "DeskripsiWisatawayScreen/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("deskripsi") { type = NavType.StringType },
                navArgument("images") { type = NavType.StringType },
                navArgument("hargaTiket") { type = NavType.StringType },
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
            val imagesString = backStackEntry.arguments?.getString("images") ?: ""
            val images = if (imagesString.isNotEmpty()) imagesString.split(",")
                .mapNotNull { it.toIntOrNull() } else emptyList()
            val hargaTiket = backStackEntry.arguments?.getString("hargaTiket") ?: ""
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

            DeskripsiWisatawayScreen(
                nama = nama,
                deskripsi = deskripsi,
                images = images,
                hargaTiket = hargaTiket,
                onNavigateToMap = {
                    val encodedGoogleMapsUrl = Uri.encode(googleMapsUrl)
                    val route = "MapsScreenway/$encodedGoogleMapsUrl"
                    navController.navigate(route)
                }
            )
        }
        composable("home") {
            val currentUser = null
            HomeScreen(navController, currentUser)
        }
        composable("event_list") { EventList(navController) }
        composable(
            route = "event_detail/{eventTitle}",
            arguments = listOf(navArgument("eventTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventTitle = backStackEntry.arguments?.getString("eventTitle")
            EventDetailScreen(navController = navController, eventTitle = eventTitle)
        }
        composable(
            route = "MapsScreenway/{googleMapsUrl}",
            arguments = listOf(
                navArgument("googleMapsUrl") { type = NavType.StringType }
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""
            MapsScreenway(googleMapsUrl = googleMapsUrl)
        }

    }
}

@Composable
fun SplashScreen(navController: NavController) {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000L) // 2 seconds delay
        showSplash = false

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            navController.navigate("HomeScreen") {
                popUpTo("splashScreen") { inclusive = true }
            }
        } else {
            navController.navigate("loginScreen") {
                popUpTo("splashScreen") { inclusive = true }
            }
        }
    }

    if (showSplash) {
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
                modifier = Modifier.size(200.dp)
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
                                    navController.navigate("HomeScreen") {
                                        popUpTo("loginScreen") { inclusive = true }
                                    }
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
                    text = buildAnnotatedString {
                        append("Klik daftar sekarang")
                        addStyle(style = SpanStyle(color = Color.Blue), start = 0, end = 20)
                    },

                    onClick = {
                        navController.navigate("RegisterScreen")
                    },
                    modifier = Modifier.padding(4.dp).background(Color.Transparent)
                        .padding(4.dp).wrapContentSize()
                )
            }
        }
    }
}


package com.example.lampungexplore
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun LampungExploreApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "daftar_wisata") {
        composable("daftar_wisata") {
            DaftarWisataLampungUtaraScreen(navController = navController)
        }
        composable("DeskripsiWisataScreen/{nama}/{deskripsi}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val deskripsi = backStackEntry.arguments?.getString("deskripsi")
            DeskripsiWisataScreen(nama = nama ?: "", deskripsi = deskripsi ?: "")
        }
    }
}

@Composable
fun DaftarWisataLampungUtaraScreen(navController: NavController) {
    val wisataList = listOf(
        Wisata("Bendungan Way Rarem", "Deskripsi Bendungan Way Rarem: Pesona bendungan yang indah...", R.drawable.wayrarem),
        Wisata("Taman Nasional Way Kambas", "Deskripsi Taman Nasional Way Kambas: Taman nasional yang terkenal dengan gajahnya...", R.drawable.kerangmas),
        Wisata("Pantai Kuala Kambas", "Deskripsi Pantai Kuala Kambas: Pantai yang menawarkan pemandangan alam yang indah...", R.drawable.kerangmas),
        // Tambahkan wisata lainnya sesuai kebutuhan
    )

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Daftar Wisata di Lampung Utara",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Black
            )

            LazyColumn {
                items(wisataList) { wisata ->
                    WisataItem(wisata = wisata, navController = navController)
                }
            }
        }
    }
}

@Composable
fun WisataItem(wisata: Wisata, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("DeskripsiWisataScreen/${wisata.nama}/${wisata.deskripsi}")
            },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = wisata.image),
                contentDescription = wisata.nama,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = wisata.nama,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun DeskripsiWisataScreen(nama: String, deskripsi: String) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = nama,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Black
            )
            Text(
                text = deskripsi,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

data class Wisata(
    val nama: String,
    val deskripsi: String,
    val image: Int
)

@Preview
@Composable
fun PreviewLampungExploreApp() {
    val navController = rememberNavController()
    LampungExploreApp(navController = navController)
}
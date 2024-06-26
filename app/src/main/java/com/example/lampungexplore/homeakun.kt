package com.example.lampungexplore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun AppNavigation(navController: NavHostController, currentUser: FirebaseUser?) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, currentUser) }
        composable(
            route = "event_detail/{eventTitle}",
            arguments = listOf(navArgument("eventTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventTitle = backStackEntry.arguments?.getString("eventTitle")
            EventDetailScreen(navController = navController, eventTitle = eventTitle)
        }
    }
}



@Composable
fun HomeScreen(navController: NavController, currentUser: FirebaseUser?)
{
    val currentUserEmail = currentUser?.email ?: ""

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            // Ucapan selamat datang
            Text(
                text = "Halo, Selamat Datang $currentUserEmail",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Tambahkan banner gambar dengan slider animasi
            BannerSlider(
                images = listOf(
                    R.drawable.bannera,
                    R.drawable.bannerb,
                    R.drawable.bannerc
                )
            )

            // Teks wisata ke Lampung
            Text(
                text = "Yuk Explore Wisata Lampung",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        item {
            // Event Terkini di Lampung
            Text(
                text = "Event Terkini di Lampung",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            EventList(navController)
        }

        item {
            // Daftar Kabupaten di Lampung
            Text(
                text = "Kabupaten di Lampung",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            KabupatenList(navController)
        }
    }


}



@Composable
fun EventList(navController: NavController) {
    val eventList = listOf(
        Event("Festival Krakatau", "Festival Krakatau 15-20 Juni 2024", R.drawable.event3),
        Event("Lampung Fair", "Lampung Fair 11-28 Agustus 2024", R.drawable.event2),
        Event("Pesta Sekura", "Pesta Sekura 22-30 September 2024", R.drawable.event1)
        // Tambahkan event lainnya sesuai kebutuhan
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(eventList) { event ->
            EventItem(event = event, navController = navController)
        }
    }
}

data class EventDetail(
    val title: String,
    val image: Int,
    val description: String,
    val address: String
)

@Composable
fun EventDetailScreen(eventTitle: String?, navController: NavHostController) {
    // Mock event detail data; ideally, this would come from a repository or passed as a parameter
    val eventDetail = EventDetail(
        title = eventTitle ?: "Unknown Event",
        image = R.drawable.event1, // Replace with actual image resource
        description = "Festival ini diselenggarakan di PKOR WAY HALIM untuk memeriahkan Pariwisata Provinsi Lampung",
        address = "123 Event Street, Event City, Event Country"
    )

    // Display event details
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Event Title
        Text(
            text = eventDetail.title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Event Image
        Image(
            painter = painterResource(id = eventDetail.image),
            contentDescription = eventDetail.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Event Description
        Text(
            text = "Description",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = eventDetail.description,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Event Address
        Text(
            text = "Address",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = eventDetail.address,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}





@Composable
fun EventItem(event: Event, navController: NavController) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
            .clickable {
                navController.navigate("event_detail/${event.title}")
            }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = event.image),
            contentDescription = event.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = event.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = event.description,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}






data class Event(val title: String, val description: String, val image: Int)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerSlider(images: List<Int>) {
    val pagerState = remember { ViewPager2.ORIENTATION_HORIZONTAL }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(images) { index, image ->
                BannerItem(image = painterResource(id = image))
            }
        }
    }
}

@Composable
fun BannerItem(image: Painter) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .width(320.dp)
            .height(180.dp)
            .clip(MaterialTheme.shapes.large)
    ) {
        Image(
            painter = image,
            contentDescription = "Banner Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun KabupatenList(navController: NavController) {
    val kabupatenList = listOf(
        Kabupaten("Lampung Selatan", R.drawable.lampungselatan),
        Kabupaten("Lampung Utara", R.drawable.lamput),
        Kabupaten("Lampung Tengah", R.drawable.lampungtengah),
        Kabupaten("Lampung Barat", R.drawable.lampungbarat),
        Kabupaten("Lampung Timur", R.drawable.lampungtimur),
        Kabupaten("Bandar Lampung", R.drawable.balam),
        Kabupaten("Metro", R.drawable.metro),
        Kabupaten("Pringsewu", R.drawable.kabpringsewu),
        Kabupaten("Pesawaran", R.drawable.pesawaran),
        Kabupaten("Tanggamus", R.drawable.tanggamus),
        Kabupaten("Pesisir Barat", R.drawable.pesibar),
        Kabupaten("Way Kanan", R.drawable.wayakanan),
        Kabupaten("Tulang Bawang", R.drawable.tuba),
        Kabupaten("Tulang Bawang Barat", R.drawable.tubaba),
        Kabupaten("Mesuji", R.drawable.mesuji),

    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        kabupatenList.forEach { kabupaten ->
            KabupatenItem(navController, kabupaten = kabupaten)
        }
    }
}

@Composable
fun KabupatenItem(navController: NavController, kabupaten: Kabupaten) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                if (kabupaten.nama == "Lampung Utara") {
                    navController.navigate("DaftarWisataLampungUtaraScreen")
                }
                if (kabupaten.nama == "Lampung Selatan") {
                    navController.navigate("DaftarWisataLampungSelatanScreen")
                }
                if (kabupaten.nama == "Lampung Barat") {
                    navController.navigate("DaftarWisataLampungBaratScreen")
                }
                if (kabupaten.nama == "Lampung Tengah") {
                    navController.navigate("DaftarWisataLampungTengahScreen")
                }
                if (kabupaten.nama == "Lampung Timur") {
                    navController.navigate("DaftarWisataLampungTimurScreen")
                }
                if (kabupaten.nama == "Pringsewu") {
                    navController.navigate("DaftarWisataPringsewuScreen")
                }
                if (kabupaten.nama == "Pesawaran") {
                    navController.navigate("DaftarWisataPesawaranScreen")
                }
                if (kabupaten.nama == "Tanggamus") {
                    navController.navigate("DaftarWisataTanggamusScreen")
                }
                if (kabupaten.nama == "Pesisir Barat") {
                    navController.navigate("DaftarWisataPesisirbaratScreen")
                }
                if (kabupaten.nama == "Bandar Lampung") {
                    navController.navigate("DaftarWisataBandarlampungScreen")
                }
                if (kabupaten.nama == "Metro") {
                    navController.navigate("DaftarWisataMetroScreen")
                }
                if (kabupaten.nama == "Way Kanan") {
                    navController.navigate("DaftarWisataWayKananScreen")
                }
                if (kabupaten.nama == "Tulang Bawang") {
                    navController.navigate("DaftarWisataTulangBawangScreen")
                }
                if (kabupaten.nama == "Tulang Bawang Barat") {
                    navController.navigate("DaftarWisataTubabaScreen")
                }
                if (kabupaten.nama == "Mesuji") {
                    navController.navigate("DaftarWisataMesujiScreen")
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = kabupaten.icon),
                contentDescription = kabupaten.nama,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp),



            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = kabupaten.nama,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


data class Kabupaten(val nama: String, val icon: Int)

@Preview
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser
    HomeScreen(navController, currentUser)
}

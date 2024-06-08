package com.example.lampungexplore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeScreen(navController: NavController, currentUser: FirebaseUser?) {
    val currentUserEmail = currentUser?.email ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Ucapan selamat datang
        Text(
            text = "Halo, Selamat Datang $currentUserEmail",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tambahkan banner gambar dengan slider animasi
        BannerSlider(
            images = listOf(
                R.drawable.gbban,
                R.drawable.gbban,
                R.drawable.gbban
            )
        )

        // Teks wisata ke Lampung
        Text(
            text = "Yuk Wisata ke Lampung",
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        KabupatenList(navController)
    }
}

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
            .size(200.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "Banner Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    }

}

@Composable
fun KabupatenList(navController: NavController) {
    val kabupatenList = listOf(
        Kabupaten("Lampung Selatan", R.drawable.lampungutara),
        Kabupaten("Lampung Utara", R.drawable.lampungutara),
        Kabupaten("Lampung Tengah", R.drawable.lampungutara),
        Kabupaten("Lampung Barat", R.drawable.lampungutara),
        Kabupaten("Lampung Timur", R.drawable.lampungutara),
        // Tambahkan kabupaten lainnya sesuai kebutuhan
    )

    LazyColumn {
        items(kabupatenList) { kabupaten ->
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
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = kabupaten.icon),
                contentDescription = kabupaten.nama,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = kabupaten.nama)
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

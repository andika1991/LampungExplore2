package com.example.lampungexplore

import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.net.Uri
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import kotlinx.coroutines.delay

@Composable
fun pswlistApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "daftar_wisata") {
        navigation(
            route = "daftar_wisata",
            startDestination = "daftar_wisata_list"
        ) {
            composable("daftar_wisata_list") {
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
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "example://example/{nama}/{deskripsi}/{images}/{hargaTiket}/{googleMapsUrl}"
                    }
                )
            ) { backStackEntry ->
                val nama = backStackEntry.arguments?.getString("nama") ?: ""
                val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""
                val imagesString = backStackEntry.arguments?.getString("images") ?: ""
                val images = if (imagesString.isNotEmpty()) imagesString.split(",").mapNotNull { it.toIntOrNull() } else emptyList()
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
                )
            ) { backStackEntry ->
                val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

                // Display the map screen using the provided URL
                MapsScreenpsw(googleMapsUrl = googleMapsUrl)
            }
        }
    }
}

@Composable
fun DaftarWisataPringsewuScreen(navController: NavController) {
    val wisataList = listOf(
        Wisata(
            "Air Terjun Pagasan Pringsewu",
            "Air Terjun Pagasan – Tidak banyak yang tahu kalau Kabupaten Pringsewu juga menyimpan banyak potensi wisata alam. Termasuk air terun yang ternyata ada beberapa dan tersebar di daerah dengan julukan Bumi Jejama Secancanan.\n" +
                    "Air Terjun Pagasan adalah air terjun dengan dinding dan aliran air terjun yang indah. Air terjunnya tidak jatuh tegak lurus, melainkan jatuh berundak-undak di tebing bebatuannya.\n" +
                    "Tentu kita bertanya-tanya ya di mana lokasi air terjun yang belum banyak diketahui banyak orang ini.\n" +
                    "Air terjun ini berada di daerah Utara kabupaten Pringsewu. Melihat lokasinya di peta, ternyata dekat dengan Bendungan Batu Tegi.\n" +
                    "Berjarak sekitar 90 kilometer dari Kota Bandar Lampung. Waktu tempuhnya sekitar 3 jam bekendara.\n" +
                    "Sedangkan dari Pringsewu berjarak sekitar 50 kilometer. Waktu tempuhnya sekitar 2 jam dengan berkendara.\n  Kalau kamu dari arah Pringsewu, arahkan kendaraan ke Pasar Pagelaran. Lalu belok ke kanan menuju Fajar Baru dan Desa Neglasari.\n" +
                    "Kalau dari arah Sukoharjo, arahkan kendaraan menuju Banyumas Lurus dan Pagelaran Utara. Kemudian menuju ke Neglasari dan sekitar Pagasan.\n" +
                    "Dari Bandar Lampung, selain lewat Pringsewu, juga bisa lewat Tegineneng. Setelah\n" +
                    "Bandara Radin Inten II, belok ke kiri ke arah Negerikaton – Bangun Sari – Adi Luwih – Sribasuki – Pasar Kalirejo. Lalu arahkan kendaraan menuju Neglasari dan Pagasan.\n",
            listOf(R.drawable.bukitpangonan, R.drawable.bukitpangonan), // Example images
            "Gratis Tidak Dikenakan Biaya sama sekali",
            "https://maps.app.goo.gl/mZX13NFWz8PP78WX6"
        ),
        Wisata(
            "Gua maria laverna",
            "Kabupaten Pringsewu sepertinya menjadi kabupaten istimewa bagi umat Katholik di Provinsi Lampung.Sebab di kabupaten inilah umat katholik dapat menikmati wisata rohani Gua Maria Padang Bulan, Desa Fajar Esuk, Kabupaten Pringsewu, Lampung.\n" +
                    "lokasi yang memiliki julukan Maria Perempuan Untuk Semua Manusia ini sering dikunjungi peziarah dari berbagai daerah.Disebut-sebut, gua ini tidak kalah indah dengan wisata ziarah Lourdes yang berada di Perancis (destinasi wisata ziarah umat katolik paling terkenal di dunia).Tempat ziarah Gua Maria Lourdes diyakini umat katolik merupakan petilasan penampakan bunda Maria.\n" +
                    "Padang Bulan dalam bahasa Jawa yang berarti terang bulan adalah nama sebuah desa berbukit di wilayah Kelurahan Fajar Esuk, Pringsewu.Dalam struktur Gereja, Padang Bulan termasuk di dalam wilayah Paroki Santo Yusuf, Pringsewu.Wisata religi yang memiliki jarak sekitar tiga kilometer dari Pringsewu ini istimewanya masih alami.Pepohonan besar dipertahankan di sekitar areal Goa Maria, sehingga kawasan menjadi jauh dari polusi udara dan kebisingan jalan raya.\n" +
                    "Selain itu, di sekitarnya masih banyak sawah-sawah yang produktif, sehingga menambah keasrian pemandangan nan hijau.\n",
            listOf(R.drawable.guamaria, R.drawable.guamaria), // Example images
            "Tidak dikenakan Biaya Tiket Masuk",
            "https://maps.app.goo.gl/H7ne81pZL7b4t7L96"
        ),
        Wisata(
            "Tegal Gupid",
            "n dari pagi. Sebab Agrowisata Lembah Bambu Kuning akan ramai dikunjungi para wisatawan di akhir pekan.",
            listOf(R.drawable.tegalgupit, R.drawable.tegalgupit,R.drawable.tegalgupit,R.drawable.tegalgupit), // Example images
            "Harga perwahana berbeda beda 5.000 - 20.000",
            "https://maps.app.goo.gl/zvQcBsKuvnPjhuy29"
        ) ,
        Wisata(
            "Air Terjun Sukaemi",
            "Jika anda mencari air terjun di Lampung Utara dengan panorama mempesona, maka anda bisa mendatangi Air Terjun Sukaemi. Tersembunyi dan jauh dari keramaian, air terjun ini bak surga tersembunyi yang masih sangat jarang dijamah oleh wisatawan.\n" +
                    "\n" +
                    "Objek wisata ini memang masih jarang terdengar, namun sebenarnya ada panorama menakjubkan yang disuguhkan oleh Air Terjun Sukaemi. Ditambah lagi kondisi alamnya yang masih sangat asri, bakal membuat kolaborasi suasana yang jarang dijumpai di lokasi lain.\n" +
                    "\n" +
                    "Objek Air Terjun Sukaemi memang tak seterkenal Bendungan Way Rarem ataupun Tirta Shinta yang sama-sama terletak di Lampung Utara. Tapi setelah pengunjung menginjakan kaki di lokasi ini, maka keindahannya akan mampu berbicara.\n" +
                    "\n" +
                    "Alamat dan Rute Perjalanan ke Air Terjun Sukaemi\n" +
                    "Air terjun ini terletak di Dusun Sinarogan, Desa Tanjung Beringin, Kecamatan Tanjung Raja, Kabupaten Lampung Utara. Lokasinya berjarak sekitar 42 km dari Kotabumi dengan waktu tempuh sekitar satu setengah jam perjalanan.\n" +
                    "\n" +
                    "Jika dari Bandar Lampung maka jaraknya sekitar 125 km dengan waktu tempuh sekitar tiga sampai empat jam. Adapun rutenya yakni Bandar Lampung – Natar – Bandar Jaya – Kotabumi – Kecamatan Tanjung Raja- Air Terjun Sukaemi.\n" +
                    "\n" +
                    "Trasportasi umum bisa menggunakan bus atau kereta dari Bandar Lampung, namun hanya bisa menghantar sampai ke Kotabumi saja. Selebihnya perjalanan bisa dilanjutkan dengan menaiki ojek, itu pun jika ada. Oleh sebab itu menggunakan kendaraan pribadi rida dua lebih rekomended.\n" +
                    "\n" +
                    "Harga Tiket Masuk dan Fasilitas di Air Terjun Sukaemi\n" +
                    "Tidak ada biaya pungutan masuk untuk menikmati air terjun ini, fasilitas pun belum ada disekitar air terjun. Oleh sebab itu pengunjung mesti membawa perbekalan untuk mengantisipasi perut lapar seusai perjalanan dan setelah bermaiin di air terjun.\n" +
                    "\n" +
                    "Pengunjung hanya akan mengeluarkan biaya seiklasnya untuk menitipkan kendaraannya di rumah desa terakhir sebelum sampai ke air terjun ini.\n" +
                    "\n" +
                    "Keistimeawaan Air Terjun Sukaemi\n" +
                    "Jarang dijamah membuat air terjun ini masih sangat alami dan sejuk. Air terjun dengan ketinggian lebih dari 30 meter ini memiliki bentuk yang juga sangat cantik guyuran air dengan debit sangat deras beruas satu membuat air yang jatuh sangat bergemuruh.\n" +
                    "\n" +
                    "Aliran Air Terjun Curup Sukaemi ini bersumber Sungai Way Gendot, yang memiliki aliran yang cukup deras. Dan berasal dari perbukitan yang masih asri. Air sejuk itu bisa digunakan untuk membasahi badan sehingga segar kemabali.\n" +
                    "\n" +
                    "Pemandangan disekitar air terjun juga menawan, tebing disekitarnya tertutup oleh semak dan liana sehingga berwarna hijau da terlihat apik. Dibagian bawah air terjun terdapat kubangan yang bisa digunakan untuk mandi dan bermain air.\n" +
                    "\n" +
                    "Ait terjun ini juga nampak fotogenik jika digunakan untuk obek selfie. berlatar belakang air terjun yang mebah dengan ini pengunjung bisa mengambil jarak sekitar 15 meter di depan air terjun untuk berfoto.\n" +
                    "\n" +
                    "Lokasi air terjun berada di tengah-tengah perkebunan kopi warga, pemandangan perkebunan kopi yang seragam juga nampak elok.\n" +
                    "\n" +
                    "Mengunjungi air terjun ini tak hanya disugui dengan air terjunnya saja, sebelum sampai pengunjung akan diberikan suguhan pemandangn kebun kopi dan rumah-rumah tradisonal berbentuk panggung milik warga.\n" +
                    "\n" +
                    "Sebelum sampai, sesekali pengunjung bisa berhenti di bukit yang tinggi untuk melihat hamparan hijau kebun kopi yang berundak-undak ala perbukitan. Pemandangan ini akan jarang dijumpai di lokasi lain.\n" +
                    "\n" +
                    "Meski akses dan jaraknya cukup jauh, namun pemandangan mempesona air terjun akan membayar tuntas kekurangan itu. Demikianlah informasi terkait Air Terjun Sukaemi Lampung Utara, semoga bisa menjadi referensi liburan anda.",
            listOf(R.drawable.airterjunsukaemi, R.drawable.airterjunsukaemi2), // Example images
            "Tidak dikenakan Biaya Tiket Masuk",
            "https://maps.app.goo.gl/wPArozncoAKLbbb88"
        ),
        Wisata(
            " Abung River Tubing",
            "Selama perjalanan, pengunjung akan disuguhi pemandangan yang masih alami. Perkebuan, hutan, tebing dan air terjun dijamin akan memanjakan mata para pengunjung. Tebing batu di samping kanan kiri lintasan tubing nampak sangat indah dan megah, bahkan banyak pengunjung yang menyebutknya dengan grand canyon nya Lampung Utara.\n" +
                    "\n" +
                    "Jika memilih paket panjang, pengunjung akan berjumpa dengan belasan air terjun yang menawan. Sembari tubing, guyuran air terjun tersebut akan membasahi badan. Jeram-jeram di sungai juga bakal menjadi pemacu adrenalin para Pengunjung. Bahkan di beberapa titik ada jeram yang cukup ekstrem yang sangat menantang.\n" +
                    "\n" +
                    "Yang spesial juga titik pemberhentian lintasan ini berupa air terjun megah yakni Air Terjun Beringin. Setelah puas ber tubing, pengunjung akan diajak menimati air terjun dengan ketinggian lebih dari 20 meter tersebut.\n" +
                    "\n" +
                    "Keasikan tubing dan suasana alam yang masih sangat asri bisa menjadi alasan kenapa anda harus mengunjunginya. Demikianlah informasi terakit Abung River Tubing Lampung Utara, semoga bisa menjadi referensi liburan anda.",
            listOf(R.drawable.abungrivertobing, R.drawable.abungrivertubing2), // Example images
            "Paket Pendek (Short Trip)\n" +
                    "Start Point: Jembatan Halampam\n" +
                    "Finish Point : Air Terjun Beringin\n" +
                    "Biaya: Rp 50.000 per orang\n" +
                    "Paket Panjang (Long Trip)\n" +
                    "Start Point : Jembatan Halampam\n" +
                    "Post 1 : Air Terjun Beringin\n" +
                    "Post 2 : Abung Lintang\n" +
                    "Finish Point : Jembatan Temiangan\n" +
                    "Biaya: Rp. 80.000 per orang\n" +
                    "Fasilitas\n" +
                    "Pemandu\n" +
                    "Tubing Boat\n" +
                    "Helm\n" +
                    "Pelampung\n" +
                    "Protector Siku/lutut\n" +
                    "Makan & Minum\n" +
                    "Dokumentasi",
            "https://goo.gl/maps/3odjsayzUf3QkXta9"
        ) ,
    )

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Daftar Wisata di Pringsewu",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Black
            )

            LazyColumn {
                items(wisataList) { wisata ->
                    WisataItempsw(wisata = wisata, navController = navController)
                }
            }
        }
    }
}

@Composable
fun WisataItempsw(wisata: Wisata, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val imagesString = wisata.images.joinToString(",") { it.toString() }
                val encodedNama = Uri.encode(wisata.nama)
                val encodedDeskripsi = Uri.encode(wisata.deskripsi)
                val encodedHargaTiket = Uri.encode(wisata.hargaTiket)
                val encodedGoogleMapsUrl = Uri.encode(wisata.googleMapsUrl)
                val route = "DeskripsiWisataScreen/$encodedNama/$encodedDeskripsi/$imagesString/$encodedHargaTiket/$encodedGoogleMapsUrl"
                navController.navigate(route)
            },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = wisata.images.first()),
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
fun DeskripsiWisatapswScreen(
    nama: String,
    deskripsi: String,
    hargaTiket: String,
    images: List<Int>,
    onNavigateToMap: () -> Unit // Tambahkan parameter untuk menavigasi ke peta
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Menggunakan LazyColumn untuk konten yang dapat di-scroll
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = nama,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        items(images) { image ->
                            Image(
                                painter = painterResource(id = image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(240.dp)
                                    .clip(shape = RoundedCornerShape(8.dp)) // Rounded shape
                                    .padding(4.dp)
                            )
                        }
                    }

                    Text(
                        text = deskripsi,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Harga Tiket: $hargaTiket",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = onNavigateToMap,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Lihat Peta")
                    }
                }
            }
        }
    }
}


@Composable
fun MapsScreenpsw(googleMapsUrl: String) {
    // Display the map screen using the provided URL
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                loadUrl(googleMapsUrl)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

data class Wisatapsw(
    val nama: String,
    val deskripsi: String,
    val images: List<Int>, // List of image resource IDs
    val hargaTiket: String, // Ticket price
    val googleMapsUrl: String // URL for Google Maps
)

@Preview
@Composable
fun pswlistApp() {
    val navController = rememberNavController()
    LampungExploreApp(navController = navController)
}
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
fun lamsellistApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "daftar_wisata") {
        navigation(
            route = "daftar_wisata",
            startDestination = "daftar_wisata_list"
        ) {
            composable("daftar_wisata_list") {
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
                )
            ) { backStackEntry ->
                val googleMapsUrl = backStackEntry.arguments?.getString("googleMapsUrl") ?: ""

                // Display the map screen using the provided URL
                MapsScreenlamsel(googleMapsUrl = googleMapsUrl)
            }
        }
    }
}

@Composable
fun DaftarWisataLampungSelatanScreen(navController: NavController) {
    val wisataList = listOf(
        Wisata(
            "Bendungan Way Rarem",
            "Sejarah Bendungan Way Rarem\n" +
                    "Bendungan Way Rarem dibangun oleh pemerintah Lampung Utara. Memiliki jangkauan untuk mengaliri seluas 22.000 Hektar. Dengan luas bendungan sendiri 49,2 hektar serta ketinggian 59 meter merupakan bendungan terbesar di Lampung Utara.\n" +
                    "\n" +
                    "Dengan adanya bendungan ini membantu untuk irigasi pada sektor perganian di sekitar Abung Timur, Tulang Bawang Tengah, Tulang Bawang Udik, dan juga Kotabumi.\n" +
                    "\n" +
                    "Ciri khas dari lokasi tempat bendungan ini berada adalah terletak di perkampungan dan suasana alamnya masih sangat segar.\n" +
                    "\n" +
                    "Di Tanggal 14 Juli 1984, Bendungan Way Rarem diresmikan oleh Presiden kedua Indonesia, yaitu Presiden Soeharto.Lokasi Bendungan Way Rarem\n" +
                    "Bendungan ini terletak di Desa Kurun, Kecamatan Abung Pekurun, Kabupaten Lampung Utara, Lampung. Untuk anda yang ingin bertandang kesana maka dapat melakukan perjalanan dari Pusat Kota Bandar Lampung dan langsung menuju ke arah Kotabumi. Untuk dapat sampai bendungan ini dapat menggunakan kendaraan umum atau pribadi. Seperti Damri, Puspa Jaya dan kendaraan umum lainnya.\n" +
                    "\n" +
                    "Cukup banyak bus yang dari Bandar Lampung menuju Kotabumi dan sekitarnya. Jadi tidak perlu bingung untuk transportasi menuju bendungan ini.\n" +
                    "\n" +
                    "Tiket Masuk Bendungan Way Rarem\n" +
                    "Untuk masuk tempat wisata air di Lampung Utara ini anda tidak dikenakan biaya masuk. Hal ini mengingat dulu bendungan ini sempat dikelola dengan baik oleh pemerintah, namun semenjak banyak kejadian mistis dan menelan korban bendungan ini menjadi sepi pengunjung.\n" +
                    "\n" +
                    "Fasilitas Bendungan Way Rarem\n" +
                    "Dulu tempat ini sering dijadikan tempat pertunjukan dan sering didatangi oleh artis ibukota seperti Evie Tamala atau artis lainnya.\n" +
                    "\n" +
                    "Namun saat ini banyak fasilitas yang rusak antara lain seperti permainan anak-anak dan pondok wisata yang dapat dipakai untuk berteduh yang sudah terbengkalai dan dibiarkan begitu saja. Hal ini juga terlihat ketika memasuki bendungan di pos jaga yang sudah rusak.\n" +
                    "\n" +
                    "Dengan tidak dikelola kembali bendungan ini, maka saat ini ketika musim kemarau airnya berkurang. Namun ketika musim hujan pemandangan air yang dibendungan dapat mempesona siapapun yang datang melihatnya.\n" +
                    "\n" +
                    "Suara air yang gemercik saling bersautan merupakan satu dari suara yang sering terdengar di sini. Suara tersebut akan menjadikan anda merasa tenang dan seolah berada di alam bebas.\n" +
                    "\n",
            listOf(R.drawable.wayrarem, R.drawable.tamanwisatawayrarem), // Example images
            "Gratis Tidak Dikenakan Biaya sama sekali",
            "https://maps.google.com/?q=Bendungan+Way+Rarem"
        ),
        Wisata(
            "Curup Ateng",
            "Disebut dengan nama Curup Ateng karena tinggi air terjun ini sangat mini. Meski demikian, keindahan dari air terjun ini tetap dapat dinikmati. Bermodal lingkungan yang asri dan udara yang sejuk membuat atmosfer tersendiri bagi para pengunjung. Meski ukuran curup ini lebih mini namun jangan heran dengan pemandangan indah yang ada di sekitarnya. Penuh dengan tumbuhan hijau, bunga yang indah dan beberapa rumput liar yang menambah semarak suasana.\n" +
                    "\n" +
                    "Cobalah datang bersama keluarga atau orang tersayang sambil berendam di kolam bawah air terjun, pasti akan sangat sejuk dan asik. Jangan lupa mengambil foto sebagai kenangan terindah bersama orang terkasih. Alamat: Tanjung Baru, Kec. Bukit Kemuning, Kab. Lampung Utara, Lampung",
            listOf(R.drawable.curupateng, R.drawable.curupateng), // Example images
            "Tidak dikenakan Biaya Tiket Masuk",
            "https://goo.gl/maps/H32HBcBv1SZysMy56"
        ),
        Wisata(
            " Agrowisata Lembah Bambu Kuning",
            "Argowisata Lembah Bambu Kuning adalah destinasi wisata buatan yang menggabungkan beberapa wahana dan aktivitas seru untuk para wisatawan. Seperti waterboom, taman bunga, kuliner, outbound, dan wisata pemancingan. Destinasi wisata ini berlokasi di Desa Abung Jayo, Kecamatan Abung Selatan, Kabupaten Lampung Utara, Lampung.\n" +
                    "Tidak hanya itu, Agrowisata Lembah Bambu Kuning ini dibangun di tengah perkebunan karet. Hal ini membuat suasana yang tercipta masih asri, rindang, sejuk, dan segar. Daya tarik utama dari tempat ini adalah argo wisatanya. Kegiatan wisata yang menggunakan lahan pertanian atau fasilitas yang terdapat di dalamnya. Umumnya jenis wisata seperti ini menggunakan hewan dan tumbuhan untuk menarik atensi para wisatawan.\n" +
                    "Sama seperti namanya, di Agrowisata Lembah Bambu Kuning ada banyak wisata tanaman dan hewan. Seperti memetik buah dan juga memberi makan hewan. Ada beberapa paket wisata yang bisa dipilih oleh para wisatawan. Ada Paket Edukasi untuk keliling area taman dan mempelajari alam yang akan dibimbing oleh pemandu profesional.\n" +
                    "\n" +
                    "Lalu ada Paket Pelajar bagi yang tertarik untuk menikmati wahana waterboom. Terakhir ada Paket PAUD/TK yang ditujukan untuk rombongan PAUD yang ingin belajar tentang alam.Nantinya, para wisatawan akan diajak untuk melakukan pembibitan tanaman, pembibitan buah, pertanian, pembibitan ikan, wisata buah, wisata tanaman hias, dan masih banyak kegiatan seru lainnya.\n" +
                    "Agrowisata Lembah Bambu Kuning juga menyediakan berbagai wahana permainan seru lainnya. Seperti becak mini, motor mini, flying fox, jungle tracking, dan outbound di alam terbuka. Tidak hanya itu, ada banyak spot foto yang tidak boleh dilewati begitu saja. Spot foto yang paling populer adalah Jembatan Lembah Bambu Kuning atau LBK.\n" +
                    "Selain menjadi destinasi wisata, di Agrowisata Lembah Bambu Kuning juga sering diadakan acara-acara tertentu. Mulai dari pentas seni hingga kegiatan sosial. Tempatnya yang memiliki luas sekitar 4 hektar ini bisa menampung hingga ratusan orang sekaligus.\n" +
                    "\n" +
                    "HTM, Fasilitas, dan Waktu Operasional\n" +
                    "Destinasi Agrowisata Lembah Bambu Kuning yang didirikan pada tahun 2014 ini terkenal akan tanaman jambu, rambutan, dan jeruknya. Harga tiket masuk yang dikenakan adalah Rp10.000 per orang berlaku untuk orang dewasa dan anak-anak.\n" +
                    "Lalu untuk wihana waterboom dikenakan harga tiket sebesar Rp20.000 per orang. Paket wisata edukasi mulai dari Rp35.000 per orang. Jika para wisatawan ingin menikmati wisata taman bunga, harga tiket yang harus dibayar adalah Rp5.000 per orang.\n" +
                    "Sementara itu, untuk aktivitas seru lainnya seperti flying fox, mini becak, mini motor, dan outbound bisa dinikmati dengan harga mulai Rp20.000 per orang. Waktu operasional Agrowisata Lembah Bambu Kuning dimulai dari pukul 08.00 hingga 17.00. Tempat wisata ini buka setiap hari dan terbuka untuk umum.\n" +
                    "Sebagai bentuk profesionalisme, pihak pengelola Agrowisata Lembah Bambu Kuning menyediakan fasilitas umum yang lengkap. Mulai dari toilet, mushola, area parkir yang luas, kantin dan cafe, tempat makan, wahana memancing, wisata kuliner, spot foto, toko pusat oleh-oleh dan souvenir, hingga akses wifi gratis.\n" +
                    "\n" +
                    "Salah satu fakta menarik dari Agrowisata Lembah Bambu Kuning ini adalah belum diekspos secara besar-besaran. Dalam artian, pihak pengelola belum melakukan promosi di media-media besar terkait potensi wisata ini.Selama ini, promosi dilakukan hanya melalui media sosial dan dari mulut ke mulut para wisatawan yang datang. Sehingga bisa menjadi populer seperti sekarang.Berkunjung ke Agrowisata Lembah Bambu Kuning tidak hanya mengajarkan anak-anak tentang alam dan hasilnya, tetapi para wisatawan juga bisa menikmati pesona keindahan alam yang masih hijau dan asri.\n" +
                    "Jika Anda berkunjung ke tempat ini, ada baiknya datang ketika weekday dan dari pagi. Sebab Agrowisata Lembah Bambu Kuning akan ramai dikunjungi para wisatawan di akhir pekan.",
            listOf(R.drawable.lbk1, R.drawable.lbk2,R.drawable.lbk3,R.drawable.lbk4), // Example images
            "Harga perwahana berbeda beda 5.000 - 20.000",
            "https://www.google.com/maps/dir//Agrowisata+Lembah+Bambu+Kuning+5W5M%2BP7R+Abung+Jayo+Kec.+Abung+Sel.,+Kabupaten+Lampung+Utara,+Lampung+34517/@-4.840638,104.9331349,15z/data=!4m5!4m4!1m0!1m2!1m1!1s0x2e38a97ca0600b4b:0xa5f75d9e92777da8"
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
                text = "Daftar Wisata di Lampung Utara",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Black
            )

            LazyColumn {
                items(wisataList) { wisata ->
                    WisataItemlamsel(wisata = wisata, navController = navController)
                }
            }
        }
    }
}

@Composable
fun WisataItemlamsel(wisata: Wisata, navController: NavController) {
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
fun DeskripsiWisatalamselScreen(
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
fun MapsScreenlamsel(googleMapsUrl: String) {
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

data class Wisatalamsel(
    val nama: String,
    val deskripsi: String,
    val images: List<Int>, // List of image resource IDs
    val hargaTiket: String, // Ticket price
    val googleMapsUrl: String // URL for Google Maps
)

@Preview
@Composable
fun lamsellistApp() {
    val navController = rememberNavController()
    LampungExploreApp(navController = navController)
}
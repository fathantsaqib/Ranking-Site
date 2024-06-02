# RankingSite

### Pendahuluan
Aplikasi **RankingSite** adalah aplikasi Android yang memungkinkan pengguna untuk melihat peringkat klub sepak bola dan negara dalam kompetisi UEFA. Pengguna dapat mencari peringkat berdasarkan nama klub atau negara dan menandai favorit mereka untuk akses cepat di masa mendatang.

### Fitur Utama
1. **Menampilkan Peringkat Klub dan Negara**: Pengguna dapat melihat peringkat terbaru dari klub dan negara yang berpartisipasi dalam kompetisi UEFA.
2. **Pencarian**: Pengguna dapat mencari klub atau negara berdasarkan nama.
3. **Menandai Favorit**: Pengguna dapat menandai klub atau negara favorit mereka untuk akses cepat.
4. **Dukungan Offline**: Pengguna dapat melihat daftar favorit mereka bahkan ketika tidak terhubung ke internet.

### Struktur Proyek

#### Paket dan Kelas Utama
- **com.example.afinal**
  - `RankActivity`: Aktivitas utama yang mengelola tampilan peringkat negara dan klub.
  - `RankAdapter`: Adapter untuk menampilkan daftar peringkat dalam `RecyclerView`.
  - `ApiService`: Interface untuk panggilan API menggunakan Retrofit.
  - `RetrofitClient`: Kelas utilitas untuk menginisialisasi Retrofit.
  - `Rank`: Model data untuk peringkat.
  - `RankResponse`: Model data untuk respons API.
  
- **com.example.afinal.fragment**
  - `FavoriteFragment`: Fragment untuk menampilkan daftar favorit.
  - `HomeFragment` : Fragment untuk menampilkan halaman home.
  - `ProfileFragment` : Fragment untuk menampilkan profil.

### Teknologi
  - Teknologi MeowBottom: MeowBottom adalah pustaka Android yang digunakan untuk membuat Bottom Navigation Bar yang menarik dan mudah digunakan. Pustaka ini menyediakan berbagai fitur untuk kustomisasi dan animasi pada navigasi bawah aplikasi.
  - Lottie: Lottie adalah pustaka untuk menampilkan animasi JSON yang dibuat menggunakan Adobe After Effects dengan plugin Bodymovin. Animasi Lottie lebih ringan dan memberikan performa yang lebih baik dibandingkan format GIF.
  - Retrofit: Retrofit adalah pustaka type-safe HTTP client untuk Android dan Java, yang dikembangkan oleh Square. Retrofit memudahkan proses interaksi dengan API RESTful dengan konversi data otomatis.
  - Java: Untuk pengembangan aplikasi itu sendiri.

### Implementasi API
[FOOTAPI](https://rapidapi.com/fluis.lacasse/api/footapi7)

### Kesimpulan
Aplikasi RankingSite menyediakan cara yang mudah dan efisien bagi pengguna untuk melihat dan mengelola peringkat klub dan negara dalam kompetisi UEFA. Dengan fitur pencarian dan favorit, pengguna dapat dengan mudah menemukan dan menyimpan data peringkat yang mereka butuhkan. Implementasi Retrofit untuk API memudahkan pengelolaan data dari server dan tampilan yang dinamis memastikan pengalaman pengguna yang lancar.

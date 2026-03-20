# Tugas 2 - News App (Kotlin Multiplatform) 📰

Aplikasi berita sederhana yang dikembangkan menggunakan **Kotlin Multiplatform (KMP)** dan **Compose Multiplatform**. Proyek ini menampilkan daftar berita dengan antarmuka yang modern, responsif, dan menerapkan pola arsitektur **MVVM** (Model-View-ViewModel).

## 👤 Informasi Mahasiswa
* **Nama:** Jefri Wahyu Fernando Sembiring
* **NIM:** 123140206
* **Program Studi:** 
Teknik Informatika, Institut Teknologi Sumatera (ITERA)

---

## 🚀 Fitur Utama
Aplikasi ini memiliki beberapa fitur utama yang mencakup:
1.  **News List:** Menampilkan daftar berita terkini dengan gambar dan ringkasan singkat.
2.  **Modularisasi :** Pemisahan logika data melalui `NewsViewModel` dan `NewsRepository` untuk manajemen data yang lebih rapi.


---

## 🛠️ Teknologi yang Digunakan
* **Language:** Kotlin
* **Framework:** Compose Multiplatform (KMP)


---

## 📸 Tampilan Aplikasi (Screenshot)

| Home Feed | List Berita | Loading/Detail |
|:---:|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/6959fe07-0c8d-4b44-9fe6-cddaeecefb40" width="200" alt="Screen 1"/> | <img src="https://github.com/user-attachments/assets/e00b2c26-e773-469d-a813-3a6254f674af" width="200" alt="Screen 2"/> | <img src="https://github.com/user-attachments/assets/34fa41f0-fe37-4424-af7b-d94fc73326ca" width="200" alt="Screen 3"/> |

---

## 📥 Cara Menjalankan (Installation)
Ikuti langkah-langkah berikut untuk menjalankan proyek ini di perangkat lokal Anda:

1.  **Clone Repository:**
    ```bash
    git clone [https://github.com/3-206-jefri/Pemrograman_Mobile_123140206.git](https://github.com/3-206-jefri/Pemrograman_Mobile_123140206.git)
    ```
2.  **Buka di Android Studio:**
    Gunakan versi Android Studio terbaru  untuk kompatibilitas KMP yang optimal.
3.  **Setup Emulator:**
    Pastikan Anda memiliki Android Simulator atau perangkat fisik yang terhubung.
4.  **Run Project:**
    Pilih konfigurasi `composeApp` dan klik tombol **Run**.

---

## 📂 Struktur Folder Tugas 2
```text
Tugas2/
├── composeApp/
│   └── src/
│       └── commonMain/kotlin/com/example/tugas2/
│           ├── ui/ (NewsScreen.kt)
│           ├── viewmodel/ (NewsViewModel.kt)
│           ├── model/ (News.kt)
│           └── data/ (NewsRepository.kt)
└── README.md
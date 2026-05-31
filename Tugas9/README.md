# 📝 Tugas 9 - Notes APP  With AI API Integration
Aplikasi ini adalah proyek pengembangan Android/Kotlin Multiplatform (KMP) yang mengimplementasikan arsitektur Clean Code (MVVM) dan Dependency Injection (Koin). Fokus utama dari pembaruan ini adalah pengintegrasian **Google Gemini API** sebagai Asisten AI Pintar.

## ✨ Fitur Utama
- **Manajemen Catatan (CRUD):** Tambah, baca, ubah, dan hapus catatan.
- **Favorit & Pengaturan:** Menyimpan catatan favorit dan kustomisasi tampilan antarmuka (Terang/Gelap).
- 🤖 **Smart AI Assistant:** Chatbot terintegrasi yang didukung oleh Gemini API. AI ini telah diinjeksi (*Prompt Engineering*) dengan konteks catatan milik pengguna, sehingga ia dapat menjawab pertanyaan atau merangkum isi catatan pengguna secara spesifik.
- 💬 **Multi-turn Conversation (🌟 Fitur Bonus):** Chatbot memiliki memori untuk mengingat riwayat percakapan. Pengguna dapat melakukan diskusi panjang dengan AI tanpa kehilangan konteks pertanyaan sebelumnya. Antarmuka menggunakan desain *Chat Bubbles* yang responsif.
- **Robust Error Handling:** Aplikasi menangani kasus ketika *server down* atau *high demand* tanpa menyebabkan *Force Close*, serta menampilkan status *loading* yang interaktif.

## 🛠️ Teknologi & Arsitektur
- **UI Toolkit:** Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel) & Clean Architecture
- **Dependency Injection:** Koin
- **Networking:** Ktor Client
- **AI Service:** Google Gemini API ( `gemini-3.1-flash-lite` ke atas)
- **Security:** BuildKonfig (untuk menyembunyikan API Key dari public repository)

---

## 🚀 Cara Menjalankan Aplikasi (Setup API Key)

⚠️ **PENTING:** Demi keamanan, API Key tidak disertakan di dalam repository ini. Anda harus menambahkan API Key Anda sendiri agar fitur AI dapat berjalan.

Ikuti langkah-langkah berikut untuk menjalankan aplikasi di Android Studio:

1. **Clone Repository ini** ke komputer lokal Anda.
2. Dapatkan API Key secara gratis melalui [Google AI Studio](https://aistudio.google.com/).
3. Buka project ini di **Android Studio**.
4. Cari dan buka file **`local.properties`** di root folder project Anda.
5. Tambahkan baris berikut di bagian paling bawah file `local.properties`:
   ```properties
   GEMINI_API_KEY=masukkan_api_key_anda_di_sini
   (Catatan: Jangan gunakan tanda kutip pada API key)
6. Lakukan Sync Project with Gradle Files.
7. Tekan tombol Run (atau Shift + F10) untuk menjalankan aplikasi di Emulator atau perangkat fisik.
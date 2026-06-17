# Tugas 10 - Testing dan Dependency Injection (PAM)


## 📌 Deskripsi Tugas
Pada praktikum minggu ke-10 ini, proyek aplikasi catatan (*Notes App*) berbasis Kotlin Multiplatform (KMP) telah disempurnakan dengan menerapkan prinsip *Clean Architecture* tingkat lanjut. Fokus utama pada modul ini adalah:
1. **Dependency Injection (DI):** Mengimplementasikan Koin untuk manajemen *dependency* yang lebih rapi pada ViewModel, Repository, dan konfigurasi platform.
2. **Unit Testing:** Melakukan pengujian logika bisnis secara terisolasi menggunakan `kotlin.test`, `MockK` (untuk *mocking* repository), dan `Turbine` (untuk pengujian *Flow*).
3. **UI Testing:** Melakukan pengujian antarmuka Jetpack Compose menggunakan `createComposeRule` yang dijalankan murni secara lokal menggunakan **Robolectric** (bypass Emulator Android 14/15 restrictions).

## ✅ Daftar Test Cases (12 Tests Passed)

### Fase 1 & 2: NotesViewModelTest (4 Test Cases)
- `testInitialUiStateIsLoading`: Memastikan *state* awal antarmuka adalah `Loading`.
- `testSearchQueryFlowUpdatesCorrectly`: Memastikan *Flow* pencarian dengan *Turbine* memancarkan *value* yang benar saat *query* berubah.
- `testAddNoteCallsRepository`: Memastikan pemanggilan `addNote()` mengeksekusi fungsi *insert* pada Repository tepat 1 kali (*Verify*).
- `testDeleteNoteCallsRepository`: Memastikan pemanggilan `deleteNote()` mengeksekusi fungsi *delete* pada Repository tepat 1 kali (*Verify*).

### Fase 3: NoteRepositoryTest (5 Test Cases)
- `test1_GetAllNotes_ReturnsData`: Memastikan repository dapat mengambil daftar catatan (*Flow*) dengan data yang valid.
- `test2_GetAllNotes_ReturnsEmptyList`: Memastikan repository menangani kondisi basis data kosong dengan benar.
- `test3_InsertNote_IsCalledCorrectly`: Memastikan fungsionalitas tambah catatan pada sisi sumber data tereksekusi.
- `test4_DeleteNote_IsCalledCorrectly`: Memastikan fungsionalitas hapus data dapat menerima parameter *ID* (*Int*).
- `test5_InsertNote_WithEmptyStrings`: Menguji stabilitas repository saat menerima masukan string kosong.

### Fase 4: NotesUiTest (3 Test Cases)
- `uiTest1_EmptyStateIsDisplayed`: Memastikan komponen UI peringatan "Belum ada catatan" berhasil dirender saat data kosong.
- `uiTest2_NoteItemIsDisplayed`: Memastikan *item card* catatan dapat dimunculkan dengan teks yang sesuai di layar.
- `uiTest3_TitleInputExists`: Memastikan kotak *input* teks untuk penambahan judul (OutlinedTextField) berhasil ditampilkan.

---

## 🏆 Code Coverage Report (Bonus > 80%)
Seluruh pengujian di atas telah mencakup (*cover*) sebagian besar logika inti aplikasi. Berikut adalah bukti eksekusi *Test Passed* dan *Coverage Report*:

![Coverage Report](coverage_report.png)

---

## 🎥 Video Demo Eksekusi Testing
Berikut adalah rekam layar (*Screen Record*) berdurasi singkat yang menunjukkan seluruh pengujian berjalan sukses (*Run All Tests*) di ruang kerja IDE:

* [Klik di sini untuk melihat video demo](https://drive.google.com/file/d/1SmG5YquvRPlKrP4O0nOgsol4IvJVAH2T/view?usp=sharing)*
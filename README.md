# 🛒 Aplikasi Penjualan Berbasis Java GUI dan MySQL

Project Akhir Pemrograman Berorientasi Objek (PBO) untuk membangun aplikasi desktop pengelola data barang, pelanggan, dan transaksi penjualan secara interaktif. Aplikasi ini dirancang menggunakan antarmuka **Java Swing** dan diintegrasikan dengan pangkalan data **MySQL**.

## 🚀 Fitur Utama
- **Sistem Login:** Keamanan akses pengguna yang divalidasi langsung ke pangkalan data.
- **CRUD Data Barang:** Fungsionalitas untuk menambah, membaca, mengubah, dan menghapus inventori.
- **CRUD Data Pelanggan:** Manajemen data pembeli yang terintegrasi.
- **Transaksi Penjualan:** Sistem keranjang belanja dengan kalkulasi subtotal otomatis dan pengurangan stok seketika (*real-time*).
- **Laporan Penjualan:** Rekapitulasi histori keseluruhan transaksi dengan fitur *refresh* data.

## 🛠️ Teknologi & Implementasi Konsep
- **Bahasa Pemrograman:** Java (GUI menggunakan `javax.swing`)
- **Database:** MySQL
  - *Mengimplementasikan fitur lanjutan:* `Stored Procedure`, `Function`, `Trigger`, dan `View`.
- **Koneksi:** Java Database Connectivity (JDBC)
- **Konsep PBO (OOP):** Menerapkan arsitektur `Class`, `Object`, `Encapsulation`, `Inheritance`, `Polymorphism`, penggunaan `Package`, dan `Exception Handling`.

---

## 📸 Dokumentasi Antarmuka Aplikasi

### 1. Halaman Login
Halaman awal untuk autentikasi pengguna sebelum masuk ke sistem.
<br>
<img width="690" alt="Halaman Login" src="https://github.com/user-attachments/assets/ea4e7d7d-960a-4b62-83d7-0405470c731d" />

### 2. Notifikasi Login Berhasil
Pesan konfirmasi (pop-up) ketika username dan password tervalidasi dengan benar.
<br>
<img width="1912" alt="Login Sukses" src="https://github.com/user-attachments/assets/1f2ccb6c-119e-4ff5-a8fa-c7fa74e14b2d" />

### 3. Menu Utama (Dashboard)
Panel navigasi utama untuk mengakses seluruh modul aplikasi Penjualan.
<br>
<img width="1024" alt="Menu Utama" src="https://github.com/user-attachments/assets/e00c4d32-8e8c-4ad8-98af-02555bf6f5e3" />

### 4. Form Kelola Data Barang
Modul untuk melakukan operasi CRUD pada inventori/stok barang.
<br>
<img width="1918" alt="Data Barang" src="https://github.com/user-attachments/assets/48a8e922-cbe3-4169-af3b-dc2bd735d41c" />

### 5. Form Kelola Data Pelanggan
Modul untuk mengelola informasi dan kontak pelanggan.
<br>
<img width="1918" alt="Data Pelanggan" src="https://github.com/user-attachments/assets/bf59ec24-387b-4ee7-a9e0-178c150d197e" />

### 6. Form Transaksi Penjualan
Modul utama untuk melakukan proses jual-beli. Menampilkan keranjang, subtotal otomatis, dan trigger pemotongan stok pada database.
<br>
<img width="1918" alt="Transaksi Penjualan" src="https://github.com/user-attachments/assets/91999ca7-1261-424a-961d-818e8b0513e7" />

### 7. Laporan Penjualan
Menampilkan rekapan seluruh transaksi gabungan dari tabel relasi (menggunakan *MySQL View*).
<br>
<img width="1918" alt="Laporan Penjualan" src="https://github.com/user-attachments/assets/00085290-1824-4c9d-a03b-782764dd8ea9" />

---

## ⚙️ Petunjuk Menjalankan Project
1. Buka MySQL/phpMyAdmin, lalu *import* atau eksekusi file `setup_db.sql` untuk membuat pangkalan data beserta seluruh *Trigger* dan *Stored Procedure*-nya.
2. Buka folder *workspace* ini menggunakan **Visual Studio Code**.
3. Pastikan Anda telah memasang ekstensi **Extension Pack for Java** dari Microsoft.
4. Jangan lupa tambahkan *driver* `mysql-connector-j.jar` ke dalam bagian **Referenced Libraries** di panel Java Projects.
5. Jalankan `FormLogin.java` yang berada di dalam *package* `view` untuk memulai aplikasi.

---
**Kelompok 4 (TI24C) - Universitas Nusa Putra**

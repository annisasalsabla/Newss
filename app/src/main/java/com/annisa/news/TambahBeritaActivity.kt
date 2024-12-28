package com.annisa.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class TambahBeritaActivity : AppCompatActivity() {
    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var btnGambar: Button
    private lateinit var btnTambah: Button
    private lateinit var imgGambar: ImageView
    private lateinit var progressBar: ProgressBar
    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_berita)

        // Inisialisasi elemen
        etJudul = findViewById(R.id.etJudul)
        etIsi = findViewById(R.id.etIsi)
        btnGambar = findViewById(R.id.btnGambar)
        btnTambah = findViewById(R.id.btnTambah)
        imgGambar = findViewById(R.id.imgGambar)
        progressBar = findViewById(R.id.progressBar)

        // Event handler untuk memilih gambar
        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop() // Memungkinkan crop gambar
                .compress(1024) // Mengompresi gambar hingga 1MB
                .maxResultSize(1080, 1080) // Resolusi maksimal gambar
                .start()
        }

        // Event handler untuk tombol tambah
        btnTambah.setOnClickListener {
            val judul = etJudul.text.toString().trim()
            val isi = etIsi.text.toString().trim()

            // Validasi input
            if (judul.isEmpty() || isi.isEmpty()) {
                Toast.makeText(this, "Judul dan isi berita tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageFile == null) {
                Toast.makeText(this, "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tampilkan progress bar
            progressBar.visibility = View.VISIBLE

            // Simpan berita (contoh dengan fungsi dummy)
            simpanBerita(judul, isi, imageFile!!)
        }
    }

    // Menangani hasil dari ImagePicker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            if (uri != null) {
                imageFile = File(uri.path ?: "")
                imgGambar.visibility = View.VISIBLE
                imgGambar.setImageURI(uri) // Menampilkan gambar di ImageView
            }
        }
    }

    private fun simpanBerita(judul: String, isi: String, file: File) {
        // Fungsi untuk menyimpan berita ke backend
        // TODO: Implementasikan koneksi ke server dan pengiriman data
        Toast.makeText(this, "Berita berhasil disimpan", Toast.LENGTH_SHORT).show()

        // Sembunyikan progress bar
        progressBar.visibility = View.GONE

        // Reset input
        etJudul.text.clear()
        etIsi.text.clear()
        imgGambar.visibility = View.GONE
    }
}

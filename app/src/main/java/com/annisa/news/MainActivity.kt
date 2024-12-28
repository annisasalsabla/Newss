package com.annisa.news

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private val storagePermission = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi tombol
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)

        // Event handler untuk tombol register
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisActivity::class.java))
            checkStoragePermission()  // Periksa izin penyimpanan ketika mengklik tombol register
        }

        // Event handler untuk tombol login
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    // Fungsi untuk memeriksa izin penyimpanan
    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Meminta izin jika belum diberikan
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                storagePermission
            )
        } else {
            // Jika izin sudah diberikan
            Toast.makeText(this, "Izin penyimpanan sudah diberikan", Toast.LENGTH_SHORT).show()
        }
    }

    // Menangani hasil permintaan izin dari pengguna
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == storagePermission) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan
                Toast.makeText(this, "Izin penyimpanan diterima", Toast.LENGTH_SHORT).show()
            } else {
                // Izin ditolak
                Toast.makeText(this, "Izin penyimpanan ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

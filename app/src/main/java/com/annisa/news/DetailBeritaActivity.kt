package com.annisa.news

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailBeritaActivity : AppCompatActivity() {
    private lateinit var imgBerita: ImageView
    private lateinit var tvJudul: TextView
    private lateinit var tvTglBerita: TextView
    private lateinit var tvRating: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var tvIsiBerita: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_berita)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgBerita = findViewById(R.id.imgBerita)
        tvJudul = findViewById(R.id.tvJudul)
        tvTglBerita = findViewById(R.id.tvTglBerita)
        tvRating = findViewById(R.id.tvRating)
        ratingBar = findViewById(R.id.ratingBar)
        tvIsiBerita = findViewById(R.id.tvIsiBerita)

        // Ambil data dari Intent
        val gambarBerita = intent.getStringExtra("gambar_berita")
        val judul = intent.getStringExtra("judul")
        val tglBerita = intent.getStringExtra("tgl_berita")
        val rating = intent.getDoubleExtra("rating", 0.0) // Default rating adalah 0.0
        val isi = intent.getStringExtra("isi")

        // Set data ke UI
        if (!gambarBerita.isNullOrEmpty()) {
            Picasso.get().load(gambarBerita).into(imgBerita)
        }
        tvJudul.text = judul ?: "Judul tidak tersedia"
        tvTglBerita.text = tglBerita ?: "Tanggal tidak tersedia"
        tvRating.text = "Rating: $rating"
        ratingBar.rating = rating.toFloat()
        tvIsiBerita.text = isi ?: "Isi berita tidak tersedia"
    }
}

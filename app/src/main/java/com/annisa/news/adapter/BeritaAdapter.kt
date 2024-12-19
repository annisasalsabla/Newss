package com.annisa.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annisa.news.R
import com.annisa.news.databinding.LayoutItemNewsBinding
import com.annisa.news.models.BeritaResponse
import com.bumptech.glide.Glide
import android.util.Log
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BeritaAdapter(private val beritaList: List<BeritaResponse.ListItems>) :
    RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder>() {

    // ViewHolder untuk mengikat layout item berita
    inner class BeritaViewHolder(val binding: LayoutItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(db_berita: BeritaResponse.ListItems) {
            // Mengikat data berita ke elemen di layout
            binding.tvJudul.text = db_berita.judul
            binding.tvTglBerita.text = db_berita.tgl_berita
            binding.tvRating.text = db_berita.rating.toString()

            Log.d("Glide URL", "Memuat URL gambar: ${db_berita.gambar_berita}")

            // Memuat gambar menggunakan Glide
            Glide.with(binding.imgBerita.context)
                .load(db_berita.gambar_berita)
                .placeholder(R.drawable.ic_launcher_background) // Placeholder saat gambar belum siap
                .error(R.drawable.ic_launcher_background) // Gambar cadangan jika gagal dimuat
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Menyimpan cache disk untuk performa
                .into(binding.imgBerita)

            // Mengatur RatingBar
            binding.ratingBar.rating = db_berita.rating.toFloat()
        }
    }

    // Menghasilkan ViewHolder untuk item berita
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val binding = LayoutItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeritaViewHolder(binding)
    }

    // Mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        val db_berita = beritaList[position]
        holder.bind(db_berita)
    }

    // Menentukan jumlah item dalam daftar berita
    override fun getItemCount(): Int = beritaList.size
}

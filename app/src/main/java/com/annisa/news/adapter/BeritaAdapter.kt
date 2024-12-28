package com.annisa.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.annisa.news.models.BeritaResponse
import com.squareup.picasso.Picasso

class BeritaAdapter(
    private val dataBerita: ArrayList<BeritaResponse.ListItems>
) : RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Inisialisasi Widget
        val imgBerita: ImageView = view.findViewById(R.id.imgBerita)
        val tvJudul: TextView = view.findViewById(R.id.tvJudul)
        val tvTglBerita: TextView = view.findViewById(R.id.tvTglBerita)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataBerita.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hasilResponse = dataBerita[position]

        // Tampilkan data
        Picasso.get().load(hasilResponse.gambar_berita).into(holder.imgBerita)
        holder.tvJudul.text = hasilResponse.judul
        holder.tvTglBerita.text = hasilResponse.tgl_berita
        holder.tvRating.text = String.format("%.1f", hasilResponse.rating) // Format dengan 1 angka desimal
        holder.ratingBar.rating = hasilResponse.rating.toFloat()

        // Klik item list berita
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailBeritaActivity::class.java).apply {
                putExtra("gambar_berita", hasilResponse.gambar_berita)
                putExtra("judul", hasilResponse.judul)
                putExtra("tgl_berita", hasilResponse.tgl_berita)
                putExtra("rating", hasilResponse.rating)
                putExtra("isi", hasilResponse.isi)
            }
            holder.imgBerita.context.startActivity(intent)
        }
    }

    fun setData(data: List<BeritaResponse.ListItems>) {
        dataBerita.clear()
        dataBerita.addAll(data)
        notifyDataSetChanged()
    }
}

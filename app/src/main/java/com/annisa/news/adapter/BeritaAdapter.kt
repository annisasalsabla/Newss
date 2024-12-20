package com.annisa.news

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
    val dataBerita: ArrayList<BeritaResponse.ListItems>
) : RecyclerView.Adapter<BeritaAdapter.ViewHolder>(){
    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        //Inisialisasi Widget
        val imgBerita = view.findViewById<ImageView>(R.id.imgBerita)
        val tvJudul = view.findViewById<TextView>(R.id.tvJudul)
        val tvTglBerita = view.findViewById<TextView>(R.id.tvTglBerita)
        val tvRating = view.findViewById<TextView>(R.id.tvRating)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_news,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataBerita.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Tampilkan data//
        val hasilResponse = dataBerita[position]
        Picasso.get().load(hasilResponse.gambar_berita).into(holder.imgBerita)
        holder.tvJudul.text = hasilResponse.judul
        holder.tvTglBerita.text = hasilResponse.tgl_berita
        holder.tvRating.text = "${hasilResponse.rating}"
        holder.ratingBar.rating = hasilResponse.rating.toFloat()

    }

    fun setData(data: List<BeritaResponse.ListItems>){
        dataBerita.clear()
        dataBerita.addAll(data)
    }
}
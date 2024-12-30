package com.annisa.news

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.annisa.news.api.ApiClient
import com.annisa.news.models.BeritaResponse
import com.annisa.news.models.DeleteBeritaResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeritaAdapter(
    private val dataBerita: ArrayList<BeritaResponse.ListItems>,
    private val onDeleteClick: (Int) -> Unit // Callback for delete, expecting Int ID
) : RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgBerita: ImageView = view.findViewById(R.id.imgBerita)
        val tvJudul: TextView = view.findViewById(R.id.tvJudul)
        val tvTglBerita: TextView = view.findViewById(R.id.tvTglBerita)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val imgDelete: ImageView = view.findViewById(R.id.imgDelete) // Add delete image
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

        // Handle delete action
        holder.imgDelete.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView.context, hasilResponse.id, position)
        }

        // Handle item click to view detail
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

    // Function to update data in the adapter
    fun setData(data: List<BeritaResponse.ListItems>) {
        dataBerita.clear()  // Clear existing data
        dataBerita.addAll(data)  // Add new data
        notifyDataSetChanged()  // Notify that data has changed, so the view can update
    }

    // Function to show delete confirmation dialog
    private fun showDeleteConfirmationDialog(context: Context?, beritaId: String, position: Int) {
        if (context == null) return  // Return if context is null

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin ingin menghapus berita ini?")

        builder.setPositiveButton("Ya") { dialog: DialogInterface, id: Int ->
            deleteBerita(beritaId, position)
        }

        builder.setNegativeButton("Tidak") { dialog: DialogInterface, id: Int ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    // Function to delete berita
    private fun deleteBerita(beritaId: String, position: Int) {
        ApiClient.apiService.deleteBerita(beritaId).enqueue(object : Callback<DeleteBeritaResponse> {
            override fun onResponse(
                call: Call<DeleteBeritaResponse>,
                response: Response<DeleteBeritaResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == true) {
                    // Successfully deleted, remove from the list and notify the adapter
                    dataBerita.removeAt(position)
                    notifyItemRemoved(position)
                    onDeleteClick(beritaId.toInt())  // Call callback to delete berita from list
                } else {
                    // Handle unsuccessful response, show an error message if needed
                }
            }

            override fun onFailure(call: Call<DeleteBeritaResponse>, t: Throwable) {
                // Handle failure (for example, show an error message to the user)
            }
        })
    }
}

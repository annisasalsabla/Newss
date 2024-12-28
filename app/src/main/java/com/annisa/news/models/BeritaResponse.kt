package com.annisa.news.models

data class BeritaResponse(
    val success: Boolean,
    val messsage: String,
    val data: ArrayList<ListItems>
) {
    data class ListItems(
        val id: String,
        val judul: String,
        val isi: String,
        val tgl_berita: String,
        val gambar_berita: String,
        val rating: Double,
    )
}

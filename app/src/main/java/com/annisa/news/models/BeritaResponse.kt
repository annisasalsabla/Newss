package com.annisa.news.models

import android.graphics.pdf.models.ListItem
import okhttp3.internal.ws.RealWebSocket

data class BeritaResponse(
    val success : Boolean,
    val messsage : String,
    val data : ArrayList<ListItems>
){
    data class ListItems(
        val id: String,
        val judul : String,
        val isi : String,
        val tgl_berita : String,
        val gambar_berita : String,
        val rating : Float,
    )
}

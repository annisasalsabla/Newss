package com.annisa.news

import com.annisa.news.api.ApiClient
import com.annisa.news.models.BeritaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.annisa.news.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.rvBerita.layoutManager = LinearLayoutManager(this)

        // Fetch berita data
        fetchBerita()
    }

    private fun fetchBerita() {
        // Gunakan apiService dari ApiClient untuk memanggil API
        ApiClient.apiService.getBerita().enqueue(object : Callback<BeritaResponse> {
            override fun onResponse(
                call: Call<BeritaResponse>,
                response: Response<BeritaResponse>
            ) {
                if (response.isSuccessful) {
                    val beritaList = response.body()?.data?.map { berita ->
                        // Tambahkan prefiks URL jika hanya nama file gambar yang diterima
                        berita.copy(gambar_berita = "http://localhost/API_BASIC/API_BASIC/images/" + berita.gambar_berita)
                    }
                    Log.d("API_RESPONSE", "Berita List: $beritaList")

                    // Tampilkan data di RecyclerView menggunakan adapter
                    binding.rvBerita.adapter = BeritaAdapter(beritaList ?: emptyList())
                } else {
                    // Menangani jika response tidak sukses
                    Toast.makeText(this@DashboardActivity, "Gagal Memuat Data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<BeritaResponse>, t: Throwable) {
                // Menangani jika request gagal
                Log.e("API_ERROR", t.message ?: "Unknown error")
                Toast.makeText(this@DashboardActivity, "Gagal Memuat Data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
    }

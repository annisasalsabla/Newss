package com.annisa.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.annisa.news.api.ApiClient
import com.annisa.news.models.BeritaResponse
import com.annisa.news.models.DeleteBeritaResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {
    private lateinit var svJudul: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvBerita: RecyclerView
    private lateinit var floatBtnTambah: FloatingActionButton
    private lateinit var beritaAdapter: BeritaAdapter
    private lateinit var imgNotFound: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        svJudul = findViewById(R.id.svJudul)
        progressBar = findViewById(R.id.progressBar)
        rvBerita = findViewById(R.id.rvBerita)
        floatBtnTambah = findViewById(R.id.floatBtnTambah)
        imgNotFound = findViewById(R.id.imgNotFound)

        // Initial fetch of data
        getBerita("")

        // Set listener for search view
        svJudul.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                getBerita(query.orEmpty())
                return true
            }
        })

        // Set listener for adding news button
        floatBtnTambah.setOnClickListener {
            val intent = Intent(this@DashboardActivity, TambahBeritaActivity::class.java)
            addBeritaResult.launch(intent) // Launch new activity for adding news
        }
    }

    // Fetch list of news articles
    private fun getBerita(judul: String) {
        progressBar.visibility = View.VISIBLE
        ApiClient.apiService.getListBerita(judul).enqueue(object : Callback<BeritaResponse> {
            override fun onResponse(call: Call<BeritaResponse>, response: Response<BeritaResponse>) {
                if (response.isSuccessful) {
                    val beritaData = response.body()!!.data
                    if (response.body()!!.success) {
                        // Pass delete action to the adapter
                        beritaAdapter = BeritaAdapter(beritaData) { beritaId ->
                            deleteBerita(beritaId) // Handle delete action from adapter
                        }
                        rvBerita.adapter = beritaAdapter
                        imgNotFound.visibility = View.GONE
                    } else {
                        // Handle empty or failed response
                        beritaAdapter = BeritaAdapter(arrayListOf()) { }
                        rvBerita.adapter = beritaAdapter
                        imgNotFound.visibility = View.VISIBLE
                    }
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<BeritaResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    // Handle the delete operation
    private fun deleteBerita(beritaId: Int) {
        ApiClient.apiService.deleteBerita(beritaId.toString()).enqueue(object : Callback<DeleteBeritaResponse> {
            override fun onResponse(
                call: Call<DeleteBeritaResponse>,
                response: Response<DeleteBeritaResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == true) {
                    // Update the news list after deletion
                    Toast.makeText(this@DashboardActivity, "Berita deleted successfully", Toast.LENGTH_SHORT).show()
                    getBerita("") // Refresh news list after deletion
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to delete berita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DeleteBeritaResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val addBeritaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Refresh the berita list after successfully adding a new berita
            getBerita("")
        }
    }
}

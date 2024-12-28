package com.annisa.news.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Pastikan BASE_URL memiliki trailing slash "/" di akhir
    private const val BASE_URL = "http://192.168.100.90/"

    // Fungsi untuk membuat OkHttpClient dengan Interceptor untuk logging
    private fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Log semua request dan response body

        return OkHttpClient.Builder()
            .addInterceptor(logging) // Tambahkan interceptor logging
            .build()
    }

    // Properti Retrofit instance yang menggunakan OkHttpClient
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Tentukan base URL dengan trailing slash
            .client(provideOkHttpClient()) // Tambahkan OkHttpClient yang sudah dikonfigurasi
            .addConverterFactory(GsonConverterFactory.create()) // Converter JSON menggunakan Gson
            .build()
    }

    // Properti ApiService instance
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java) // Membuat instance ApiService
    }
}

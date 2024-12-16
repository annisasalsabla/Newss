package com.annisa.news.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Ensure the BASE_URL ends with a trailing slash
    private const val BASE_URL = "http://192.168.56.1/API_BASIC/"


    // Configure the OkHttpClient
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json") // Sets content type for API requests
                .build()
            chain.proceed(request) // Processes the request
        }
        .build()

    // Create a Retrofit instance and initialize ApiService
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL
            .client(client) // Attach the client
            .addConverterFactory(GsonConverterFactory.create()) // Add GSON converter
            .build()
            .create(ApiService::class.java) // Create an instance of the ApiService
    }
}

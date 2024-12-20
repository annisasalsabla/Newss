package com.annisa.news.api

import com.annisa.news.models.BeritaResponse
import com.annisa.news.models.LoginResponse
import com.annisa.news.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // Endpoint untuk Register
    @FormUrlEncoded
    @POST("API_BASIC/API_BASIC/register.php")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String, // Fixed typo (from fullanme to fullname)
        @Field("email") email: String,
    ): Call<RegisterResponse>

    // Endpoint untuk Login
    @FormUrlEncoded
    @POST("API_BASIC/API_BASIC/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    // Endpoint untuk Mendapatkan Data Berita
    @GET("API_BASIC/API_BASIC/get_berita.php")
    fun getListBerita(@Query("judul") judul:String): Call<BeritaResponse>
}



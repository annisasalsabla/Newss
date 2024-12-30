package com.annisa.news.api

import com.annisa.news.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // Endpoint untuk Register
    @FormUrlEncoded
    @POST("API_BASIC/API_BASIC/register.php")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String,
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
    fun getListBerita(@Query("judul") judul: String): Call<BeritaResponse>

    // Endpoint untuk Menambah Berita
    @Multipart
    @POST("API_BASIC/API_BASIC/add_berita.php")
    fun addBerita(
        @Part("judul") judul: RequestBody,
        @Part("isiBerita") isiBerita: RequestBody,
        @Part fileGambar: MultipartBody.Part
    ): Call<TambahBeritaResponse>

    @FormUrlEncoded
    @POST("API_BASIC/API_BASIC/delete_berita.php")
    fun deleteBerita(@Field("id") id: String): Call<DeleteBeritaResponse>  // Change id to String
}

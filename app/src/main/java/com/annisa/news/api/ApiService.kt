package com.annisa.news.api

import com.annisa.news.models.LoginRequest
import com.annisa.news.models.LoginResponse
import com.annisa.news.models.RegisterRequest
import com.annisa.news.models.RegisterResponse
import retrofit2.Call // Corrected import
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register.php")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login.php")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}

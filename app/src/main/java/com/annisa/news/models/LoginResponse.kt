package com.annisa.news.models

data class LoginResponse(
    val success : Boolean,
    val message : String,
    val username: String?  // Make sure this exists
)

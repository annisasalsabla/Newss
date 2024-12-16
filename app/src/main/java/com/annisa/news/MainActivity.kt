package com.annisa.news

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            // Replace TargetActivity::class.java with the actual class you want to open
            startActivity(Intent(this, RegisActivity::class.java))
        }
    }
}

package com.example.booking_listview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.booking_listview.R.id.txtLinksignup

class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        val txtLinksignup = findViewById<TextView>(R.id.txtLinksignup)
        txtLinksignup.setOnClickListener{
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }
    }
}
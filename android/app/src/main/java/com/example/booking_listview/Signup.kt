package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.signup)

        val btnSignupconfirm = findViewById<Button>(R.id.btnSignupconfirm)
        btnSignupconfirm.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java )
            startActivity(intent)
        }
        val txtLinkLogin = findViewById<TextView>(R.id.txtLinklogin)
        txtLinkLogin.setOnClickListener{
            val intent = Intent(this,Login::class.java )
            startActivity(intent)
        }
    }
}
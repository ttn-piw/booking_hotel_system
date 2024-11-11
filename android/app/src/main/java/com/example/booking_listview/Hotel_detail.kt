package com.example.booking_listview

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Hotel_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.hotel_detail)

        val hotel_img = intent.getIntExtra("HotelImage", 0)
        val hotel_name = intent.getStringExtra("HotelName")
        val hotel_address = intent.getStringExtra("HotelAddress")
        val hotel_phone = intent.getStringExtra("HotelPhone")
        val hotel_star = intent.getStringExtra("HotelStar")

        val hotelHD = findViewById<LinearLayout>(R.id.imgHD)
        val nameHD = findViewById<TextView>(R.id.txtHotelDetail)
        val starHD = findViewById<TextView>(R.id.txtStarHD)
        val addressHD = findViewById<TextView>(R.id.txtHDAddress)
        val phoneHD = findViewById<TextView>(R.id.txtHDPhone)

        hotelHD.setBackgroundResource(hotel_img)
        nameHD.text = hotel_name
        starHD.text= "$hotel_star/5"
        addressHD.text = hotel_address
        phoneHD.text = "Contact: $hotel_phone"
    }
}
package com.example.booking_listview

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.booking_listview.R.id

class RoomDetail : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.room_detail)

        val hotelName = intent.getStringExtra("hotelName")
        val roomStar = intent.getStringExtra("roomStar")
        val roomPrice = intent.getStringExtra("roomPrice")
        val roomImage = intent.getIntExtra("roomImage", 0)

        val imgRoomDetail = findViewById<LinearLayout>(R.id.imgRoomDetail)
        val txtRoomDetail = findViewById<TextView>(id.txtHotelDetail)
        val txtStarDetail = findViewById<TextView>(id.txtStarDetail)
        val txtPriceDetail = findViewById<TextView>(id.txtPriceDetail)

        txtRoomDetail.text = hotelName
        txtStarDetail.text = roomStar
        txtPriceDetail.text = roomPrice
        imgRoomDetail.setBackgroundResource(roomImage)
    }
}
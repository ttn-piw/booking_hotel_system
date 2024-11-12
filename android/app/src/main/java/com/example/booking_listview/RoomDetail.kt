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

        val hotelName = intent.getStringExtra("RHotelName")
        val roomName = intent.getStringExtra("RoomName")
        val roomStar = intent.getStringExtra("RoomStar")
        val roomPrice = intent.getStringExtra("RoomPrice")
        val roomMaxPrice = roomPrice.toString().toInt() + 500;
        val roomImage = intent.getIntExtra("roomImage", 0)

        val imgRoomDetail = findViewById<LinearLayout>(R.id.imgRoomDetail)
        val txtRoomDetail = findViewById<TextView>(id.txtRoomName)
        val txtRHotelName = findViewById<TextView>(id.txtRHotelName)
        val txtStarDetail = findViewById<TextView>(id.txtStarDetail)
        val txtPriceDetail = findViewById<TextView>(id.txtPriceDetail)

        txtRoomDetail.text = "$roomName Room"
        txtRHotelName.text = hotelName
        txtStarDetail.text = "$roomStar/5"
        txtPriceDetail.text = "$roomPrice - $roomMaxPrice$"
//        imgRoomDetail.setBackgroundResource(roomImage)
    }
}
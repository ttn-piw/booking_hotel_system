package com.example.booking_listview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        val roomDes = intent.getStringExtra("RoomDes")
        val roomPrice = intent.getStringExtra("RoomPrice")
        val roomMaxPrice = roomPrice.toString().toInt() + 500;
        val roomImage = intent.getIntExtra("RoomImage", 0)
        Log.d("RoomImgDT",roomImage.toString());

        val imgRoomDetail = findViewById<LinearLayout>(R.id.imgRoomDetail)
        val txtRoomDetail = findViewById<TextView>(id.txtRoomName)
        val txtRHotelName = findViewById<TextView>(id.txtRHotelName)
        val txtStarDetail = findViewById<TextView>(id.txtStarDetail)
        val txtPriceDetail = findViewById<TextView>(id.txtPriceDetail)
        val txtRoomDescription = findViewById<TextView>(R.id.txtRDescription);

        txtRoomDetail.text = "$roomName Room"
        txtRHotelName.text = hotelName
        txtRoomDescription.text = roomDes
        txtStarDetail.text = "$roomStar/5"
        txtPriceDetail.text = "$roomPrice - $roomMaxPrice$"
        imgRoomDetail.setBackgroundResource(roomImage)

        val btnAddToWishList = findViewById<ImageView>(R.id.btnAddToWishList)
        btnAddToWishList.setOnClickListener{
            val intent = Intent(this,WishList::class.java)
            startActivity(intent)
        }
    }
}
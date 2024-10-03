package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.R.*
import com.example.booking_listview.adapter.RvAdapter
import com.example.booking_listview.adapter.RvRoomAdapter
import com.example.booking_listview.model.OutDataRecycleView
import com.example.booking_listview.model.OutDataRecycleView_Rooms

class Mainpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.main_page)

        val list_hotel = mutableListOf<OutDataRecycleView>()
        list_hotel.add(OutDataRecycleView(drawable.hotel1,"ABC Hotel","4.9/5.0","1000$"))
        list_hotel.add(OutDataRecycleView(drawable.hotel2,"BBC Hotel","4.8/5.0","2000$"))
        list_hotel.add(OutDataRecycleView(drawable.hotel3,"CCC Hotel","4.99/5.0","3000$"))
        list_hotel.add(OutDataRecycleView(drawable.hotel1,"ABC Hotel","4.9/5.0","1000$"))
        list_hotel.add(OutDataRecycleView(drawable.hotel2,"BBC Hotel","4.8/5.0","2000$"))
        list_hotel.add(OutDataRecycleView(drawable.hotel3,"CCC Hotel","4.99/5.0","3000$"))

        val adapter_mainpage = RvAdapter(list_hotel)

        val rvHotelList = findViewById<RecyclerView>(id.rvHotelList)
        rvHotelList.adapter = adapter_mainpage
        rvHotelList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val list_room = mutableListOf<OutDataRecycleView_Rooms>()
        list_room.add(OutDataRecycleView_Rooms(drawable.room1,"Standard","3.0/5.0","500$","Hotel 1"))
        list_room.add(OutDataRecycleView_Rooms(drawable.room2,"Standard","3.5/5.0","1500$","Hotel 3"))
        list_room.add(OutDataRecycleView_Rooms(drawable.room3,"Standard","4.0/5.0","2500$","Hotel 5"))
        list_room.add(OutDataRecycleView_Rooms(drawable.room4,"Standard","3.5/5.0","3500$","Hotel 2"))

        val adapter_rooms = RvRoomAdapter(list_room) { room ->
            val intent = Intent(this, RoomDetail::class.java)
            intent.putExtra("hotelName", room.HotelName)
            intent.putExtra("roomStar", room.RoomStar)
            intent.putExtra("roomPrice", room.RoomPrice)
            intent.putExtra("roomImage", room.RoomImage)
            startActivity(intent)
        }

        val rvRoomsList = findViewById<RecyclerView>(id.rvRoomList)
        rvRoomsList.adapter = adapter_rooms
        rvRoomsList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

//        val btnBack = findViewById<Button>(id.btnBack)
//        btnBack.setOnClickListener{
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}
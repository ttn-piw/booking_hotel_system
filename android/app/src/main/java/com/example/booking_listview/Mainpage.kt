package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.booking_listview.R.*
import com.example.booking_listview.adapter.RvAdapter
import com.example.booking_listview.databinding.ActivityMainBinding
import com.example.booking_listview.adapter.RvRoomAdapter
import com.example.booking_listview.model.OutDataRecycleView
import com.example.booking_listview.model.OutDataRecycleView_Rooms
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.Room
import org.json.JSONObject


class Mainpage : AppCompatActivity() {

    private lateinit var rvHotelList: androidx.recyclerview.widget.RecyclerView
    private lateinit var RvAdapter: RvAdapter
    private lateinit var rvRoomList: RecyclerView
    private lateinit var RvRoomAdapter: RvRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        val sharedPreferences = getSharedPreferences("emailSession", MODE_PRIVATE)
        val emailSession = sharedPreferences.getString("session_email","No email found")

        val textSessionEmail = findViewById<TextView>(R.id.edtSessionEmail)
        textSessionEmail.text = "Welcome, $emailSession! Where do you want to go?"

        getPID(emailSession.toString())

        rvHotelList = findViewById(R.id.rvHotelList)
        rvHotelList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        fetchBestHotels()

        rvRoomList = findViewById(R.id.rvRoomList)
        rvRoomList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        fetchBestRooms()

        val btnBooked = findViewById<ImageView>(R.id.btnBookedRooms)
        btnBooked.setOnClickListener{
            val intent = Intent(this,Booked_page::class.java)
            startActivity(intent);
        }
    }

    private fun fetchBestHotels() {
        val url = "http://10.0.2.2:8080/hotels/api/bestHotels"
        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val hotelsList = mutableListOf<Hotel>()
                for (i in 0 until response.length()) {
                    val hotelJson = response.getJSONObject(i)
                    val hotel = Hotel(
                        hname = hotelJson.getString("hname"),
                        hphone = hotelJson.getString("hphone"),
                        hstar = hotelJson.getString("hstar"),
                        hid = hotelJson.getInt("hid"),
                        himg = hotelJson.getString("himg"),
                        haddress = hotelJson.getString("haddress"),
                        hdes = hotelJson.getString("hdescription")
                    )
                    hotelsList.add(hotel)
                }

                RvAdapter = RvAdapter(hotelsList)
                rvHotelList.adapter = RvAdapter
            },
            { error ->
                Toast.makeText(this, "Failed to load hotels: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonArrayRequest)
    }

    private fun fetchBestRooms(){
        val url = "http://10.0.2.2:8080/rooms/api/bestRooms"
        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val roomsList = mutableListOf<Room>()
                for (i in 0 until response.length()) {
                    val roomJson = response.getJSONObject(i)
                    val hotelJson = roomJson.getJSONObject("hotels")

                    val hotel = Hotel(
                        hname = hotelJson.getString("hname"),
                        hphone = hotelJson.getString("hphone"),
                        hstar = hotelJson.getString("hstar"),
                        himg = hotelJson.getString("himg"),
                        hid = hotelJson.getInt("hid"),
                        haddress = hotelJson.getString("haddress"),
                        hdes = hotelJson.getString("hdescription")
                    )
                    val room = Room(
                        ctgid = roomJson.getInt("ctgid"),
                        ctgstar = roomJson.getString("ctgstar"),
                        ctgprice = roomJson.getString("ctgprice"),
                        ctgname = roomJson.getString("ctgname"),
                        ctgimg = roomJson.getString("ctgimg"),
                        ctgremain =  roomJson.getInt("ctgremain"),
                        ctghid = hotel,
                        ctgquantity = roomJson.getInt("ctgquantity"),
                        ctgdes = roomJson.getString("ctgdescription")
                    )

                    roomsList.add(room)
                }

                RvRoomAdapter = RvRoomAdapter(roomsList)
                rvRoomList.adapter = RvRoomAdapter
            },
            { error ->
                Toast.makeText(this, "Failed to load rooms: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    }

    private fun getPID(emailSession: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/persons/getPID/personEmail?personEmail=$emailSession"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    if (response.length() > 0) {

                        val firstItem = response.getJSONArray(0)

                        val pid = firstItem.getInt(0)
                        val personName = firstItem.getString(1)
                        val address = firstItem.getString(2)

                        Log.d("PID", pid.toString())
                        Log.d("NAME_P", personName);
                        Log.d("ADDRESS", address)

                        val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
                        val editor = sharedPreferences.edit();
                        editor.putString("session_pid", pid.toString());
                        editor.putString("session_name", personName)
                        editor.putString("session_address", personName)
                        editor.apply();

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        queue.add(jsonArrayRequest)
    }
}

//class Mainpage : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(layout.main_page)
//
//        val list_hotel = mutableListOf<OutDataRecycleView>()
//        list_hotel.add(OutDataRecycleView(drawable.hotel1,"ABC Hotel","4.9/5.0","1000$"))
//        list_hotel.add(OutDataRecycleView(drawable.hotel2,"BBC Hotel","4.8/5.0","2000$"))
//        list_hotel.add(OutDataRecycleView(drawable.hotel3,"CCC Hotel","4.99/5.0","3000$"))
//        list_hotel.add(OutDataRecycleView(drawable.hotel1,"ABC Hotel","4.9/5.0","1000$"))
//        list_hotel.add(OutDataRecycleView(drawable.hotel2,"BBC Hotel","4.8/5.0","2000$"))
//        list_hotel.add(OutDataRecycleView(drawable.hotel3,"CCC Hotel","4.99/5.0","3000$"))
//
//        val adapter_mainpage = RvAdapter(list_hotel)
//
//        val rvHotelList = findViewById<RecyclerView>(id.rvHotelList)
//        rvHotelList.adapter = adapter_mainpage
//        rvHotelList.layoutManager = LinearLayoutManager(
//            this,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//
//        val list_room = mutableListOf<OutDataRecycleView_Rooms>()
//        list_room.add(OutDataRecycleView_Rooms(drawable.room1,"Standard","3.0/5.0","500$","Hotel 1"))
//        list_room.add(OutDataRecycleView_Rooms(drawable.room2,"Standard","3.5/5.0","1500$","Hotel 3"))
//        list_room.add(OutDataRecycleView_Rooms(drawable.room3,"Standard","4.0/5.0","2500$","Hotel 5"))
//        list_room.add(OutDataRecycleView_Rooms(drawable.room4,"Standard","3.5/5.0","3500$","Hotel 2"))
//
//        val adapter_rooms = RvRoomAdapter(list_room) { room ->
//            val intent = Intent(this, RoomDetail::class.java)
//            intent.putExtra("hotelName", room.HotelName)
//            intent.putExtra("roomStar", room.RoomStar)
//            intent.putExtra("roomPrice", room.RoomPrice)
//            intent.putExtra("roomImage", room.RoomImage)
//            startActivity(intent)
//        }
//
//        val rvRoomsList = findViewById<RecyclerView>(id.rvRoomList)
//        rvRoomsList.adapter = adapter_rooms
//        rvRoomsList.layoutManager = LinearLayoutManager(
//            this,
//            LinearLayoutManager.VERTICAL,
//            false
//        )

//        val btnBack = findViewById<Button>(id.btnBack)
//        btnBack.setOnClickListener{
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }

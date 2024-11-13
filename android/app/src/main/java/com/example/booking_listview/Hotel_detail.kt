package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.booking_listview.adapter.RvRoomAdapter
import com.example.booking_listview.adapter.RvRoomHotelIDAdapter
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.Room
import org.json.JSONException
import org.json.JSONObject

class Hotel_detail : AppCompatActivity() {

    private lateinit var RvRoomHotelIDAdapter: RvRoomHotelIDAdapter
    private lateinit var rvAvailableRoomList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.hotel_detail)

        val hotel_id = intent.getIntExtra("HotelId",0)
        val hotel_img = intent.getIntExtra("HotelImage", 0)
        val hotel_name = intent.getStringExtra("HotelName")
        val hotel_address = intent.getStringExtra("HotelAddress")
        val hotel_phone = intent.getStringExtra("HotelPhone")
        val hotel_star = intent.getStringExtra("HotelStar")
        val hotel_des = intent.getStringExtra("HotelDes")

        val hotelHD = findViewById<LinearLayout>(R.id.imgHD)
        val nameHD = findViewById<TextView>(R.id.txtHotelDetail)
        val starHD = findViewById<TextView>(R.id.txtStarHD)
        val addressHD = findViewById<TextView>(R.id.txtHDAddress)
        val phoneHD = findViewById<TextView>(R.id.txtHDPhone)
        val hotelDes = findViewById<TextView>(R.id.txtHDDescribe)

        hotelHD.setBackgroundResource(hotel_img)
        nameHD.text = hotel_name
        starHD.text= "$hotel_star/5"
        addressHD.text = hotel_address
        hotelDes.text = hotel_des
        phoneHD.text = "Contact: $hotel_phone"


        rvAvailableRoomList = findViewById(R.id.rvAvailableRoomList)
        rvAvailableRoomList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false,
        )
        fetchAvailableRooms(hotel_id)

    }

    private fun fetchAvailableRooms(hotel_id: Int) {
        val url = "http://10.0.2.2:8080/rooms/hotelId?hotel_id=$hotel_id"
        Log.d("HOTEL_ID", hotel_id.toString())
        val intent = Intent(this, RoomDetail::class.java)
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
                    Log.d("Room Info", room.toString())
                    Log.d("Hotel Info", hotel.toString())

                    roomsList.add(room)
                }

                RvRoomHotelIDAdapter = RvRoomHotelIDAdapter(roomsList)
                rvAvailableRoomList.adapter = RvRoomHotelIDAdapter
            },
            { error ->
                Toast.makeText(this, "Failed to load rooms: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    }
}


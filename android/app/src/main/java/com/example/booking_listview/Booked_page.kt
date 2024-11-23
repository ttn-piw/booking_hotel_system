package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.booking_listview.adapter.BookingAdapter
import com.example.booking_listview.model.Booking

class Booked_page : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingAdapter: BookingAdapter
    private val bookings = mutableListOf<Booking>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booked_page)

        recyclerView = findViewById(R.id.recyclerViewBooked)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookingAdapter = BookingAdapter(bookings)
        recyclerView.adapter = bookingAdapter

        val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE)
        val pidSession = sharedPreferences.getString("session_pid","No email found")

        pidSession?.toInt()?.let { fetchBookings(it) }
        val btnHomeBP = findViewById<Button>(R.id.btnHomeBP)
        btnHomeBP.setOnClickListener {
            val intent = Intent(this, Mainpage::class.java)
            startActivity(intent);
        }
    }

    private fun fetchBookings(personId: Int) {
        val url = "http://10.0.2.2:8080/bookings/api/personId?personId=$personId"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                bookings.clear()
                for (i in 0 until response.length()) {
                    val bookingJson = response.getJSONArray(i)
                    val booking = Booking(
                        hname = bookingJson.getString(0),
                        ctgname = bookingJson.getString(1),
                        total_money = bookingJson.getString(2),
                        check_in = bookingJson.optString(3, null),
                        check_out = bookingJson.optString(4, null)
                    )
                    bookings.add(booking)
                    bookingAdapter.notifyDataSetChanged()
                    Log.d("BOOKINGS_DATA", bookings.toString())
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        queue.add(jsonArrayRequest)
    }
}
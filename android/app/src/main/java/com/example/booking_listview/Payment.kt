package com.example.booking_listview

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.Person
import com.example.booking_listview.model.Room

class Payment : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Find Views
//        val tvHotelName = findViewById<TextView>(R.id.tvHotelName)
//        val tvHotelAddress = findViewById<TextView>(R.id.tvHotelAddress)
//        val tvRoomDetails = findViewById<TextView>(R.id.tvRoomDetails)
//        val tvUserDetails = findViewById<TextView>(R.id.tvUserDetails)
//        val btnCancel = findViewById<Button>(R.id.btnCancel)
//        val btnConfirmBooking = findViewById<Button>(R.id.btnConfirmBooking)

        // Mock Data
        val hotel = Hotel(
            hname = "Luxury Hotel",
            himg = "https://example.com/hotel.jpg",
            hid = 1,
            hphone = "123-456-7890",
            hstar = "5 Stars",
            haddress = "123 Main St, City, Country",
            hdes = "A luxurious hotel experience."
        )

        val room = Room(
            ctgid = 101,
            ctgname = "Deluxe Suite",
            ctgimg = "https://example.com/room.jpg",
            ctgremain = 5,
            ctgquantity = 1,
            ctgstar = "5 Stars",
            ctgprice = "$250/night",
            ctghid = hotel,
            ctgdes = "Spacious room with all amenities."
        )

        val user = Person(
            PID = 1001,
            PName = "John Doe"
        )

//        // Bind Data to Views
//        tvHotelName.text = hotel.hname
//        tvHotelAddress.text = "Address: ${hotel.haddress}"
//        tvRoomDetails.text = """
//            Room: ${room.ctgname}
//            Price: ${room.ctgprice}
//            Stars: ${room.ctgstar}
//        """.trimIndent()
//
//        tvUserDetails.text = "User: ${user.PName}"
//
//        // Button Actions
//        btnCancel.setOnClickListener {
//            finish() // Close activity on cancel
//        }
//
//        btnConfirmBooking.setOnClickListener {
//            // Handle booking confirmation logic
//        }
    }
}

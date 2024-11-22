package com.example.booking_listview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.Person
import com.example.booking_listview.model.Room
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class Payment : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val hotelName = intent.getStringExtra("HotelNamePM")
        val hotelAddress = intent.getStringExtra("HotelAddressPM")
        val hotelImage = intent.getStringExtra("HotelImgPM")
        val roomNameIntent = intent.getStringExtra("RoomNamePM")
        val roomImage = intent.getIntExtra("RoomImgPM", -1)
        val roomPriceIntent = intent.getStringExtra("RoomPricePM")
        val checkInDate = intent.getStringExtra("CheckIn")
        val checkOutDate = intent.getStringExtra("CheckOut")
        val totalMoney = intent.getLongExtra("TotalMoney",0)

        Log.d("RIMG_PM",roomImage.toString())
        Log.d("HIMG_PM",hotelImage.toString())

        // Find Views
//        val tvHotelName = findViewById<TextView>(R.id.tvHotelName)
//        val tvHotelAddress = findViewById<TextView>(R.id.tvHotelAddress)
//        val tvRoomDetails = findViewById<TextView>(R.id.tvRoomDetails)
//        val tvUserDetails = findViewById<TextView>(R.id.tvUserDetails)
//        val btnCancel = findViewById<Button>(R.id.btnCancel)
//        val btnConfirmBooking = findViewById<Button>(R.id.btnConfirmBooking)

        val createAt = findViewById<TextView>(R.id.txtCreateAt)

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formattedDateTime = dateFormat.format(calendar.time)

//        TAKE_ID
        val txtUNamePM = findViewById<TextView>(R.id.txtUserName)
        val txtEmailPM = findViewById<TextView>(R.id.txtUserEmail)
        val txtAddressPM = findViewById<TextView>(R.id.txtUserAddress)
        val txtCheckInDate = findViewById<TextView>(R.id.txtCheckInDate)
        val txtCheckOutDate = findViewById<TextView>(R.id.txtCheckOutDate)
        val imgHotel = findViewById<ImageView>(R.id.imgHotelPM)
        val hotelNamePM = findViewById<TextView>(R.id.txtHotelNamePM)
        val hotelAddressPM = findViewById<TextView>(R.id.txtHotelAddressPM)
        val imgRoom = findViewById<ImageView>(R.id.imgRoomPM)
        val roomName = findViewById<TextView>(R.id.txtRoomNamePM)
        val roomPrice = findViewById<TextView>(R.id.txtRoomPricePM)
        val totalMoneyPM = findViewById<TextView>(R.id.txtTotalMoneyPM)
        val btnCancelPM = findViewById<Button>(R.id.btnCancelPM)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmPM)

//TAKE SESSION
        val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE)
        val user_session_name = sharedPreferences.getString("session_name","No name found")
        val user_session_address = sharedPreferences.getString("session_address","No address found")

        val sharedPreferences_1 = getSharedPreferences("emailSession", MODE_PRIVATE)
        val user_session_email = sharedPreferences_1.getString("session_email","No email found")
//BINDING_DATA
        createAt.text = formattedDateTime
        txtUNamePM.text = "Name: $user_session_name"
        txtEmailPM.text = "Email: $user_session_email"
        txtAddressPM.text = "Address: $user_session_address"
        txtCheckInDate.text = "Check in: $checkInDate"
        txtCheckOutDate.text = "Check out: $checkOutDate"


        val imageResId = resources.getIdentifier(hotelImage.toString().split(".")[0], "drawable", this.packageName)
        if (imageResId != 0) {
            imgHotel.setImageResource(imageResId)
        } else {
            imgHotel.setImageResource(R.drawable.not_found)
        }

        hotelNamePM.text = hotelName
        hotelAddressPM.text = hotelAddress
        roomName.text = "$roomNameIntent Room"
        imgRoom.setImageResource(roomImage)
        roomPrice.text = "$roomPriceIntent$/night"
        totalMoneyPM.text = totalMoney.toString()

        btnCancelPM.setOnClickListener{
            val intent = Intent(this,Mainpage::class.java)
            startActivity(intent)
        }

        btnConfirm.setOnClickListener {
            val intent = Intent(this, Mainpage::class.java)

            val pid = 123
            val ctgid = 456
            val hid = 789
            val money = totalMoney.toString()
            val checkInDate = txtCheckInDate.toString()
            val checkOutDate = txtCheckOutDate.toString()

            val url = "http://10.0.2.2:8080/bookings/booked"

            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    Log.d("Booking", "Booking successfully!: $response")
                    startActivity(intent)
                },
                Response.ErrorListener { error ->
                    Log.e("Booking", "Booking error: ${error.message}")
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = mutableMapOf<String, String>()
                    params["pid"] = pid.toString()
                    params["ctgid"] = ctgid.toString()
                    params["hid"] = hid.toString()
                    params["money"] = money
                    params["checkInDate"] = checkInDate
                    params["checkOutDate"] = checkOutDate
                    return params
                }
            }

            val queue = Volley.newRequestQueue(this)
            queue.add(stringRequest)
            startActivity(intent)
        }

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

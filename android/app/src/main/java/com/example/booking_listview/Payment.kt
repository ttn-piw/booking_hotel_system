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
import com.android.volley.Request
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

        val hidIntent = intent.getStringExtra("HID_RD")
        val hotelName = intent.getStringExtra("HotelNamePM")
        val hotelAddress = intent.getStringExtra("HotelAddressPM")
        val hotelImage = intent.getStringExtra("HotelImgPM")
        val ctgidIntet = intent.getStringExtra("CTGID_RD")
        val roomNameIntent = intent.getStringExtra("RoomNamePM")
        val roomImage = intent.getIntExtra("RoomImgPM", -1)
        val roomPriceIntent = intent.getStringExtra("RoomPricePM")
        val checkInDate = intent.getStringExtra("CheckIn")
        val checkOutDate = intent.getStringExtra("CheckOut")
        val totalMoney = intent.getStringExtra("TotalMoney")


        Log.d("RIMG_PM",roomImage.toString())
        Log.d("HIMG_PM",hotelImage.toString())
        Log.d("RPRICE_PM", totalMoney.toString())


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
        val txtTotalMoneyPM = findViewById<TextView>(R.id.txtTotalMoneyPM)
        val btnCancelPM = findViewById<Button>(R.id.btnCancelPM)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmPM)

//TAKE SESSION
        val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE)
        val user_session_name = sharedPreferences.getString("session_name","No name found")
        val user_session_address = sharedPreferences.getString("session_address","No address found")
        val user_pid = sharedPreferences.getString("session_pid", "No found PID")

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
        txtTotalMoneyPM.text = "$totalMoney$"

//HANDLE BUTTON EVENT
        btnCancelPM.setOnClickListener{
            val intent = Intent(this,Mainpage::class.java)
            startActivity(intent)
        }

        btnConfirm.setOnClickListener {
            val intent = Intent(this, Mainpage::class.java)

            val pid = user_pid?.toInt()
            val ctgid = ctgidIntet?.toInt()
            val hid = hidIntent?.toInt()
            val money = totalMoney.toString()
            val checkInDate = checkInDate.toString()
            val checkOutDate = checkOutDate.toString()

            Log.d("CTGID_PM", ctgid.toString())
            Log.d("PID_PM", pid.toString())
            Log.d("HID_PM", hid.toString())
            Log.d("M_PM", money.toString())
            Log.d("CI_PM", checkInDate.toString())
            Log.d("CO_PM", checkOutDate.toString())

            val params = HashMap<String, String>()
            val url = "http://10.0.2.2:8080/bookings/booked"

            params["pid"] = pid.toString()
            params["ctgid"] = ctgid.toString()
            params["hid"] = hid.toString()
            params["money"] = money.toString()
            params["checkInDate"] = checkInDate.toString()
            params["checkOutDate"] = checkOutDate.toString()

            val request = object : StringRequest(
                Request.Method.POST, url,
                { response ->
                    Log.d("Booking", "Response: $response")
                },
                { error ->
                    Log.e("Booking", "Error: ${error.message}")
                }
            ) {
                override fun getParams(): Map<String, String> = params
            }
            Volley.newRequestQueue(this).add(request)
            startActivity(intent)
        }
    }
}

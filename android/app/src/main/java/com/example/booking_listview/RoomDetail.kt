package com.example.booking_listview

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.booking_listview.R.id
import java.util.Calendar

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

        /////////////////////////DATE//////////////////////////////////////
        val txtCheckInDate = findViewById<TextView>(R.id.txtCheckInDate)
        val txtCheckOutDate = findViewById<TextView>(R.id.txtCheckOutDate)

        txtCheckInDate.setOnClickListener {
            showDatePicker { date ->
                txtCheckInDate.text = date
            }
        }

        txtCheckOutDate.setOnClickListener {
            showDatePicker { date ->
                txtCheckOutDate.text = date
            }
        }

        //////////////////////////////////////////////////////////////////////////////
        val btnAddToWishList = findViewById<ImageView>(R.id.btnAddToWishList)
        btnAddToWishList.setOnClickListener{
            val intent = Intent(this,WishList::class.java)
            startActivity(intent)
        }

        val btnBooking = findViewById<Button>(R.id.btnBookingRD)
        btnBooking.setOnClickListener {
            val intent = Intent(this,Payment::class.java)
            startActivity(intent);
        }

    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}
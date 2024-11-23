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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.booking_listview.R.id
import com.example.booking_listview.adapter.RvReviewAdapter
import com.example.booking_listview.adapter.RvRoomHotelIDAdapter
import com.example.booking_listview.model.Review
import com.example.booking_listview.model.Room
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RoomDetail : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")

    private lateinit var RvReviewAdapter: RvReviewAdapter
    private lateinit var rvAvailableReviewList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.room_detail)

        val hidRD = intent.getStringExtra("HID")
        val hotelName = intent.getStringExtra("RHotelName")
        val hotelAddress = intent.getStringExtra("RHotelAddress")
        val ctgidRD = intent.getStringExtra("CTGID")
        val roomName = intent.getStringExtra("RoomName")
        val roomStar = intent.getStringExtra("RoomStar")
        val roomDes = intent.getStringExtra("RoomDes")
        val roomPrice = intent.getStringExtra("RoomPrice")
        val roomMaxPrice = roomPrice.toString().toInt() + 500;
        val roomImage = intent.getIntExtra("RoomImage", 0)
        val hotelImage = intent.getStringExtra("HotelImg")

        Log.d("RoomImgDT",roomImage.toString());
        Log.d("HotelIMG",hotelImage.toString());
        Log.d("PRICE", roomPrice.toString())

        val imgRoomDetail = findViewById<LinearLayout>(R.id.imgRoomDetail)
        val txtRoomDetail = findViewById<TextView>(id.txtRoomName)
        val txtRHotelName = findViewById<TextView>(id.txtRHotelName)
        val txtStarDetail = findViewById<TextView>(id.txtStarDetail)
        val txtPriceDetail = findViewById<TextView>(id.txtPriceDetail)
        val txtRoomDescription = findViewById<TextView>(R.id.txtRDescription);

        val roomPrice_int = roomPrice?.toIntOrNull()  // Safe conversion
        Log.d("PRICE_INT", roomPrice_int.toString())

        txtRoomDetail.text = "$roomName Room"
        txtRHotelName.text = hotelName
        txtRoomDescription.text = roomDes
        txtStarDetail.text = "$roomStar/5"
        txtPriceDetail.text = "$roomPrice - $roomMaxPrice$"
        imgRoomDetail.setBackgroundResource(roomImage)

/////////////////////////DATE////////////////////////////////////
        val txtCheckInDate = findViewById<TextView>(R.id.txtCheckInDate)
        val txtCheckOutDate = findViewById<TextView>(R.id.txtCheckOutDate)

        var checkInDate: Date? = null
        var checkOutDate: Date? = null
        var daysBetween: Long = 0
        var totalMoney: Long = 0

        txtCheckInDate.setOnClickListener {
            showDatePicker { date ->
                txtCheckInDate.text = date
                checkInDate = parseDate(date)
            }
        }

        txtCheckOutDate.setOnClickListener {
            showDatePicker { date ->
                txtCheckOutDate.text = date
                checkOutDate = parseDate(date)

                checkInDate?.let { startDate ->
                    checkOutDate?.let { endDate ->
                        daysBetween = calculateDaysBetween(startDate, endDate)
                        Log.d("DAYS: ", "Days Between: $daysBetween")
                        if (roomPrice_int != null) {
                            totalMoney = daysBetween * roomPrice_int.toInt()
                            Log.d("TOTAL", totalMoney.toString())
                        }
                    }
                }
            }
        }

        rvAvailableReviewList = findViewById(R.id.rvAvailableReviewList)
        rvAvailableReviewList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false,
        )
        val parseCTGID = ctgidRD?.toInt()
        parseCTGID?.toInt()?.let { getReviewsFromCTGID(it) }


////////////////////////////////////////////////////////////////////////////
        val btnAddToWishList = findViewById<ImageView>(R.id.btnAddToWishList)
        btnAddToWishList.setOnClickListener{
            val intent = Intent(this, WishList::class.java)
            startActivity(intent)
        }

        val btnBooking = findViewById<Button>(R.id.btnBookingRD)
        btnBooking.setOnClickListener {
            val checkInDateString = txtCheckInDate.text.toString()
            val checkOutDateString = txtCheckOutDate.text.toString()

            val intent = Intent(this, Payment::class.java)
            intent.putExtra("HID_RD", hidRD)
            intent.putExtra("CTGID_RD", ctgidRD)
            intent.putExtra("TotalMoney", totalMoney.toString())
            intent.putExtra("HotelNamePM", hotelName)
            intent.putExtra("HotelAddressPM", hotelAddress)
            intent.putExtra("HotelImgPM", hotelImage)
//            intent.putExtra("RoomIDPM", ro)
            intent.putExtra("RoomNamePM", roomName)
            intent.putExtra("RoomImgPM", roomImage)
            intent.putExtra("RoomPricePM", roomPrice)
            intent.putExtra("CheckIn", checkInDateString)
            intent.putExtra("CheckOut", checkOutDateString)
            startActivity(intent)
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
                val formattedDate = "$selectedYear/${selectedMonth + 1}/$selectedDay"
                onDateSelected(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun parseDate(date: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return try {
            dateFormat.parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calculateDaysBetween(startDate: Date, endDate: Date): Long {
        val diffInMillis = endDate.time - startDate.time // mili seconds
        return diffInMillis / (1000 * 60 * 60 * 24)
    }

    private fun getReviewsFromCTGID(ctgid: Int) {
        Log.d("RV_CTGID", ctgid.toString())

        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/api/reviews/roomId?roomId=$ctgid"

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val dataArray = response.getJSONArray("data")
                    val reviewsList = mutableListOf<Review>()

                    for (i in 0 until dataArray.length()) {
                        val reviewObj = dataArray.getJSONObject(i)

                        val review = Review(
                            review_id = reviewObj.getInt("review_id"),
                            ctgname = reviewObj.getString("CTGName"),
                            pname = reviewObj.getString("PName"),
                            rating = reviewObj.getString("rating"),
                            rating_text = reviewObj.getString("rating_text")
                        )

                        Log.d("LIST_INFO", review.toString())
                        Log.d("RID", review.review_id.toString())
                        Log.d("CTGNAME", review.ctgname)
                        Log.d("RTEXT", review.rating_text)

                        reviewsList.add(review)
                    }

                    RvReviewAdapter = RvReviewAdapter(reviewsList)
                    rvAvailableReviewList.adapter = RvReviewAdapter

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        queue.add(jsonRequest)
    }

}